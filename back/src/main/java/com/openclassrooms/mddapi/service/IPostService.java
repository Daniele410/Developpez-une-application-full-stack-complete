package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.model.Post;

import java.util.List;

public interface IPostService {
    Post getPostById(Long id);

    List<Post> getAllPosts();

    Post savePost(Post post);
}
