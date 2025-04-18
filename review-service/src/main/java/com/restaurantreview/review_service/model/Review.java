package com.restaurantreview.review_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "review")
@Data
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private Long userId;
    private Long restaurantId;

    private Integer rating;
    private String comment;

    @CreationTimestamp
    private LocalDateTime createdAt;

}
