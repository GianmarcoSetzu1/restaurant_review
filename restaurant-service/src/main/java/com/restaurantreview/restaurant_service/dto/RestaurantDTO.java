package com.restaurantreview.restaurant_service.dto;

import com.restaurantreview.restaurant_service.model.RestaurantType;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class RestaurantDTO {
  @NotBlank String name;

  @URL(message = "It must be a valid URL")
  String url;

  RestaurantType type;
}
