package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.service.ICommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * CommentController is a REST controller that handles HTTP requests related to comments.
 * It uses ICommentService to perform operations on comments.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class CommentController {

    private final ICommentService commentService;

    /**
     * Retrieves all comments.
     *
     * @return a ResponseEntity containing a list of CommentResponseDTOs
     */
    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponseDTO>> retrieveAllComments() {
        List<Comment> comments = commentService.getAllComments();
        List<CommentResponseDTO> responseDTOs = comments.stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        log.info("retrieved all comments");
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Retrieves comments by post ID.
     *
     * @param postId the ID of the post
     * @return a ResponseEntity containing a list of CommentResponseDTOs
     */
    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentResponseDTO>> retrieveCommentsByPostId(@PathVariable Long postId) {
        Optional<List<Comment>> comments = commentService.getCommentsByPostId(postId);
        List<CommentResponseDTO> responseDTOs = comments.orElse(null).stream()
                .map(CommentResponseDTO::new)
                .collect(Collectors.toList());
        log.info("retrieved comments by post id");
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Saves a comment.
     *
     * @param comment a CommentResponseDTO containing the comment data
     * @return a ResponseEntity containing the saved Comment
     */
    @PostMapping("/comment")
    public ResponseEntity<Comment> saveComment(@RequestBody @Valid CommentResponseDTO comment) {
        Comment savedComment = commentService.saveComment(comment);
        log.info("comment saved");
        return ResponseEntity.ok(savedComment);
    }

}
