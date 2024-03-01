package com.openclassrooms.mddapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @JsonIgnore
    @OneToOne(
            fetch = FetchType.EAGER
    )
    @JoinColumn(nullable = false, name = "author_id", referencedColumnName = "id")
    private User author;


    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id", referencedColumnName = "id")
    private Post posts;
}
