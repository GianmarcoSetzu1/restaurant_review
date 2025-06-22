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

  @Column(unique = true)
  String name;

  String url;
  RestaurantType type;
  @CreationTimestamp private LocalDateTime createdAt;

  public void setName(String name) {
    this.name = name != null ? name.toUpperCase() : null;
  }
}
