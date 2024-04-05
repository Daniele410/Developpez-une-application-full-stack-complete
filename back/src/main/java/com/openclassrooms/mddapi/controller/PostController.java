package com.openclassrooms.mddapi.controller;

import com.openclassrooms.mddapi.dto.PostResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.service.IPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * PostController is a REST controller that handles HTTP requests related to posts.
 * It uses IPostService to perform operations on posts.
 */
@CrossOrigin(origins = "*")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class PostController {

    private final IPostService postService;

    /**
     * Retrieves all posts.
     *
     * @return a ResponseEntity containing a list of PostResponseDTOs
     */
    @GetMapping("/posts")
    public ResponseEntity<List<PostResponseDTO>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();
        List<PostResponseDTO> responseDTOs = posts.stream()
                .map(PostResponseDTO::new)
                .collect(Collectors.toList());
        log.info("retrieved all posts");
        return ResponseEntity.ok(responseDTOs);
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param id the ID of the post
     * @return a ResponseEntity containing the PostResponseDTO
     */
    @GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDTO> getPostById(@PathVariable Long id) {
        Post post = postService.getPostById(id);
        PostResponseDTO responseDTO = new PostResponseDTO(post);
        log.info("retrieved post with id: " + id);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Saves a post.
     *
     * @param post a PostResponseDTO containing the post data
     * @return a ResponseEntity containing the saved PostResponseDTO
     * @throws TopicNotFoundException if the topic associated with the post is not found
     */
    @PostMapping("/posts")
    public ResponseEntity<PostResponseDTO> savePost(@RequestBody PostResponseDTO post) throws TopicNotFoundException {
        PostResponseDTO savedPost = postService.savePost(post);
        log.info("post saved");
        return ResponseEntity.ok(savedPost);
    }

}
