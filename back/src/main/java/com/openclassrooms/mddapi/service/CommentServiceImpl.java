package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private IPostService postService;

    @Autowired
    private IUserService userService;

    @Override
    public Comment getCommentById(Long id) {
        log.info("get comment by id");
        return commentRepository.findById(id).get();
    }

    @Override
    public Optional<List<Comment>> getCommentsByPostId(Long postId) {

        if (postService.getPostById(postId) == null) {
            log.error("Post not found");
            return Optional.empty();
        }
        log.info("get comments by post id");
        return commentRepository.findByPostsId(postId);
    }

    @Override
    public List<Comment> getAllComments() {
        log.info("get all comments");
        return commentRepository.findAll();
    }

    @Override
    public Comment saveComment(CommentResponseDTO commentDto) {
        log.info("save comment");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Post post = postService.getPostById(commentDto.getPostId());
        Comment comment = Comment.builder()
                .description(commentDto.getDescription())
                .author(user)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .posts(post)
                .build();
        commentRepository.save(comment);
        return comment;
    }

    @Override
    public String deleteComment(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Comment comment = commentRepository.findById(id).orElseThrow();
        if (comment.getAuthor().getId() == user.getId()) {
            commentRepository.deleteById(id);
            log.info("Comment deleted successfully");
            return "Comment whit id " + id + " deleted successfully";
        } else {
            log.error("User is not the author of the comment");
            return "This User is not the author of the comment";
        }
    }

}
