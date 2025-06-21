package com.restaurantreview.restaurant_service.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  String name;
  String url;
  RestaurantType type;
  @CreationTimestamp private LocalDateTime createdAt;
}
