package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Comment;
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
    private Long authorId;
    private Long postId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.authorId = comment.getAuthorId();
        this.postId = comment.getPostId();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }
}
