package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * UserResponseDTO is a data transfer object that represents a User.
 * It is used to transfer data between different parts of the application.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private List<Topic> topic;
    @JsonIgnore
    private User user;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    /**
     * Constructs a UserResponseDTO from a User.
     *
     * @param user the User to be converted into a UserResponseDTO
     */
    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.topic = user.getTopics();
        this.created_at = user.getCreatedAt();
        this.updated_at = user.getUpdatedAt();

    }
}
