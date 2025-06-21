package com.restaurantreview.review_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "id", table = "user_account")
  private Long userId;

  @JoinColumn(name = "id", table = "restaurant")
  private Long restaurantId;

  @Min(0)
  @Max(10)
  private Float rating;

  private String comment;

  @CreationTimestamp private LocalDateTime createdAt;
}
