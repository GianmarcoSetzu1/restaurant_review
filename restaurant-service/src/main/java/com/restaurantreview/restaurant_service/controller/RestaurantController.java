package com.restaurantreview.restaurant_service.controller;

import com.restaurantreview.restaurant_service.dto.RestaurantDTO;
import com.restaurantreview.restaurant_service.model.Restaurant;
import com.restaurantreview.restaurant_service.service.JwtService;
import com.restaurantreview.restaurant_service.service.RestaurantService;
import io.jsonwebtoken.JwtException;
import jakarta.validation.Valid;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
  @Autowired private RestaurantService restaurantService;

  @Autowired private JwtService jwtService;

  @PostMapping("/create")
  public ResponseEntity<Restaurant> createRestaurant(
      @RequestHeader("Authorization") String authHeader,
      @Valid @RequestBody RestaurantDTO request) {
    try {
      jwtService.verifyAuth(authHeader);
      Restaurant restaurant = restaurantService.createRestaurant(request);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (JwtException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping("/restaurant/{id}")
  public ResponseEntity<RestaurantDTO> getRestaurantById(
      @RequestHeader("Authorization") String authHeader, @PathVariable Long id) {
    try {
      jwtService.verifyAuth(authHeader);
      RestaurantDTO response = restaurantService.getRestaurantById(id);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (JwtException e) {
      return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    } catch (NoSuchElementException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
