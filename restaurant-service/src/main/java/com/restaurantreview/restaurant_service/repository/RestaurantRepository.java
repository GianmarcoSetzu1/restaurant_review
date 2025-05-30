package com.restaurantreview.restaurant_service.repository;

import com.restaurantreview.restaurant_service.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {}
