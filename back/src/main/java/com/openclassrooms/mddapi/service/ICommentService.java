package com.openclassrooms.mddapi.service;

import com.openclassrooms.mddapi.dto.CommentResponseDTO;
import com.openclassrooms.mddapi.model.Comment;

import java.util.List;
import java.util.Optional;

public interface ICommentService {
    Comment getCommentById(Long id);

    Optional<List<Comment>> getCommentsByPostId(Long postId);

    List<Comment> getAllComments();

    Comment saveComment(CommentResponseDTO commentResponseDTO);

    String deleteComment(Long id);
}
