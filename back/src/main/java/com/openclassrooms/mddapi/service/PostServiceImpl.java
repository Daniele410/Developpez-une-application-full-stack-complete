package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements IPostService{

    private static PostRepository postRepository;

    private static ITopicService topicService;

    private static IUserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, IUserService userService, ITopicService topicService) {
        PostServiceImpl.postRepository = postRepository;
        PostServiceImpl.userService = userService;
        PostServiceImpl.topicService = topicService;
    }

    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public PostResponseDTO savePost(PostResponseDTO postResponseDTO) throws TopicNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.getUserByEmail(userDetails.getUsername());
        Topic topic = topicService.getTopicByTitle(postResponseDTO.getTopics()).orElseThrow(() -> new TopicNotFoundException("Topic not found"));
        Post post = Post.builder()
                .title(postResponseDTO.getTitle())
                .description(postResponseDTO.getDescription())
                .author(user)
                .topics(topic)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        postRepository.save(post);
        return postResponseDTO;
    }


}
