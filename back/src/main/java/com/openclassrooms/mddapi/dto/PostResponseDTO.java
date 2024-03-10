package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponseDTO {

    private long id;
    private String title;
    private String description;
    @JsonIgnore
    private User authors;
    private String topics;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public PostResponseDTO(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.description = post.getDescription();
        this.authors = post.getAuthor();
        this.topics = post.getTopics().getTitle();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
    }

}
