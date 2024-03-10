package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.PostResponseDTO;
import com.openclassrooms.mddapi.exception.TopicNotFoundException;
import com.openclassrooms.mddapi.model.Post;

import java.util.List;

public interface IPostService {
    Post getPostById(Long id);

    List<Post> getAllPosts();

    PostResponseDTO savePost(PostResponseDTO postResponseDTO) throws TopicNotFoundException;

}
