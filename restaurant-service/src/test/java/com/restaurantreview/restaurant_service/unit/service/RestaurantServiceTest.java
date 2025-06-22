package com.restaurantreview.restaurant_service.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import com.restaurantreview.restaurant_service.dto.RestaurantDTO;
import com.restaurantreview.restaurant_service.model.Restaurant;
import com.restaurantreview.restaurant_service.model.RestaurantType;
import com.restaurantreview.restaurant_service.repository.RestaurantRepository;
import com.restaurantreview.restaurant_service.service.RestaurantService;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceTest {

  @Mock private RestaurantRepository restaurantRepository;

  @InjectMocks private RestaurantService restaurantService;

  @Test
  public void getRestaurant_Success() {
    Long restaurantId = 1L;
    Restaurant restaurant = new Restaurant();
    restaurant.setId(restaurantId);
    restaurant.setName("Da Gennaro");
    restaurant.setType(RestaurantType.PIZZA);
    restaurant.setUrl("https://www.dagennaro.it");

    when(restaurantRepository.findById(1L)).thenReturn(Optional.of(restaurant));

    RestaurantDTO retrievedRestaurant = restaurantService.getRestaurantById(restaurantId);
    assertEquals(retrievedRestaurant.getName(), restaurant.getName());
    assertEquals(retrievedRestaurant.getType(), restaurant.getType());
    assertEquals(retrievedRestaurant.getUrl(), restaurant.getUrl());
    verify(restaurantRepository, times(1)).findById(restaurantId);
  }
}
