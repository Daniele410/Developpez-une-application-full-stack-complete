package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String description;
    private User authorId;
    private Post postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.authorId = comment.getAuthor();
        this.postId = comment.getPosts();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
