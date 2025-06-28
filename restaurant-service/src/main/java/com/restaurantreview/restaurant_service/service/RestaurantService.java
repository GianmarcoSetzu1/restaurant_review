package com.restaurantreview.restaurant_service.service;

import com.restaurantreview.restaurant_service.dto.IdAndNameDTO;
import com.restaurantreview.restaurant_service.dto.RestaurantDTO;
import com.restaurantreview.restaurant_service.model.Restaurant;
import com.restaurantreview.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    return RestaurantDTO.fromEntity(retrievedRestaurant);
  }

  public Page<IdAndNameDTO> searchRestaurantsByName(String partialName, PageRequest of) {
    Page<Restaurant> restaurants =
        restaurantRepository.findByNameContainingIgnoreCase(partialName, of);
    return restaurants.map(IdAndNameDTO::fromEntity);
  }
}
