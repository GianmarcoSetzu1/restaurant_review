package com.restaurantreview.restaurant_service.service;

import com.restaurantreview.restaurant_service.Repository.RestaurantRepository;
import com.restaurantreview.restaurant_service.dto.RestaurantCreationRequest;
import com.restaurantreview.restaurant_service.model.Restaurant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    public Restaurant createRestaurant(RestaurantCreationRequest request) {
        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(request.getName());
        newRestaurant.setUrl(request.getUrl());
        newRestaurant.setType(request.getType());

        return restaurantRepository.save(newRestaurant);
    }

    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.getRestaurantByRestaurantId(id);
    }

}
