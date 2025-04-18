package com.restaurantreview.user_service.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Data
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    String username;
    @Column(unique = true)
    String email;
    String password;
    @CreationTimestamp
    LocalDateTime creationTimestamp;
}
