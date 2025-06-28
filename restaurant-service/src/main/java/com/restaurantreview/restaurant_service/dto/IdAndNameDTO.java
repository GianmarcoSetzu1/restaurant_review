package com.restaurantreview.restaurant_service.dto;

import com.restaurantreview.restaurant_service.model.Restaurant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IdAndNameDTO {
  Long id;
  String name;

  public static IdAndNameDTO fromEntity(Restaurant restaurant) {
    return new IdAndNameDTO(restaurant.getId(), restaurant.getName());
  }
}
