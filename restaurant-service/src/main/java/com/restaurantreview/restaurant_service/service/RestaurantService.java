package com.restaurantreview.restaurant_service.service;

import com.restaurantreview.restaurant_service.dto.RestaurantDTO;
import com.restaurantreview.restaurant_service.model.Restaurant;
import com.restaurantreview.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

  @Autowired private RestaurantRepository restaurantRepository;

  public Restaurant createRestaurant(RestaurantDTO request) {
    Restaurant newRestaurant = new Restaurant();
    newRestaurant.setName(request.getName());
    newRestaurant.setUrl(request.getUrl());
    newRestaurant.setType(request.getType());

    return restaurantRepository.save(newRestaurant);
  }

  public RestaurantDTO getRestaurantById(Long id) {
    Restaurant retrievedRestaurant = restaurantRepository.findById(id).orElseThrow();
    RestaurantDTO restaurantDTO = new RestaurantDTO();
    restaurantDTO.setName(retrievedRestaurant.getName());
    restaurantDTO.setType(retrievedRestaurant.getType());
    restaurantDTO.setUrl(retrievedRestaurant.getUrl());
    return restaurantDTO;
  }
}
