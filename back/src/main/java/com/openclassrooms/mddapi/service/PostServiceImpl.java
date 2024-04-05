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

/**
 * PostServiceImpl is a service class that implements IPostService.
 * It provides methods to interact with the PostRepository and perform operations on Post entities.
 *
 * @author Daniele410
 * @version 1.0
 * @since 2024.1
 */
@Service
public class PostServiceImpl implements IPostService{

    private static PostRepository postRepository;

    private static ITopicService topicService;

    private static IUserService userService;

    /**
     * Constructs a new PostServiceImpl with the specified PostRepository, IUserService and ITopicService.
     *
     * @param postRepository the PostRepository to be used
     * @param userService    the IUserService to be used
     * @param topicService   the ITopicService to be used
     */
    @Autowired
    public PostServiceImpl(PostRepository postRepository, IUserService userService, ITopicService topicService) {
        PostServiceImpl.postRepository = postRepository;
        PostServiceImpl.userService = userService;
        PostServiceImpl.topicService = topicService;
    }

    /**
     * Retrieves a Post entity by its id.
     *
     * @param id the id of the Post entity to retrieve
     * @return the Post entity with the specified id
     */
    @Override
    public Post getPostById(Long id) {
        return postRepository.findById(id).get();
    }

    /**
     * Retrieves all Post entities.
     *
     * @return a list of all Post entities
     */
    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    /**
     * Creates a new Post entity from a PostResponseDTO and saves it in the repository.
     *
     * @param postResponseDTO the PostResponseDTO containing the data to create the Post entity with
     * @return the created Post entity
     * @throws TopicNotFoundException if a Topic entity with the same title already exists
     */
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
