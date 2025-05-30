package com.restaurantreview.restaurant_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
public class Restaurant {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Long restaurantId;

  String name;
  String url;
  RestaurantType type;
  @CreationTimestamp private LocalDateTime createdAt;
}
