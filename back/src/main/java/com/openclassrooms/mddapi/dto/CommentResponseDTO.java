package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * CommentResponseDTO is a data transfer object that represents a Comment.
 * It is used to transfer data between different parts of the application.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private Long id;
    private String description;
    private String author;
    private Long postId;
    private String postTitle;
    private String topicTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Constructs a CommentResponseDTO from a Comment.
     *
     * @param comment the Comment to be converted into a CommentResponseDTO
     */
    public CommentResponseDTO(Comment comment) {
        this.id = comment.getId();
        this.description = comment.getDescription();
        this.author = comment.getAuthor().getName();
        this.postId = getPostId();
        this.postTitle = comment.getPosts().getTitle();
        this.topicTitle = comment.getPosts().getTopics().getTitle();
        this.createdAt = comment.getCreatedAt();
        this.updatedAt = comment.getUpdatedAt();
    }

}
