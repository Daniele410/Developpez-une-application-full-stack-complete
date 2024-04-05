package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

/**
 * PostResponseDTO is a data transfer object that represents a Post.
 * It is used to transfer data between different parts of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

    private long id;
    private String title;
    private String description;
    private String authors;
    private String topics;
    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    /**
     * Constructs a PostResponseDTO from a Post.
     *
     * @param post the Post to be converted into a PostResponseDTO
     */
    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.authors = post.getAuthor().getName();
        this.topics = post.getTopics().getTitle();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }
}
