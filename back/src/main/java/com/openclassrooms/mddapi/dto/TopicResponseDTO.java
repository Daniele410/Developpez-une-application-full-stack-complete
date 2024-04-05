package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Topic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * TopicResponseDTO is a data transfer object that represents a Topic.
 * It is used to transfer data between different parts of the application.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TopicResponseDTO {
    Long id;
    String title;
    String description;
    Boolean isSubscribed;
    LocalDateTime created_at;
    LocalDateTime updated_at;

    /**
     * Constructs a TopicResponseDTO from a Topic.
     *
     * @param topic the Topic to be converted into a TopicResponseDTO
     */
    public TopicResponseDTO(Topic topic) {
        this.id = topic.getId();
        this.title = topic.getTitle();
        this.description = topic.getDescription();
        this.created_at = topic.getCreatedAt();
        this.updated_at = topic.getUpdatedAt();
    }
}
