package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDTO {
    private Long id;
    private String name;
    private String email;
    private List<Topic> topic;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.created_at = user.getCreatedAt();
        this.updated_at = user.getUpdatedAt();
        this.topic = user.getTopics();

    }
}
