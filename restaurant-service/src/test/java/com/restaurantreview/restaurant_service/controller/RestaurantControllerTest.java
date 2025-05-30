package com.restaurantreview.restaurant_service.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreview.restaurant_service.dto.RestaurantDTO;
import com.restaurantreview.restaurant_service.model.RestaurantType;
import com.restaurantreview.restaurant_service.service.JwtService;
import com.restaurantreview.restaurant_service.service.RestaurantService;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class RestaurantControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private JwtService jwtService;

  @MockitoBean private RestaurantService restaurantService;

  String testToken =
      "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZWdldGFAbGliZXJvLY5NTU3ODJ9Okhyt4ZX9j2ntGJJ8NkdS6v8w4mC1hGC8";

  private RestaurantDTO createTestDTO() {
    RestaurantDTO validDTO = new RestaurantDTO();
    validDTO.setName("La Contrada");
    validDTO.setUrl("https://www.lacontrada.it");
    validDTO.setType(RestaurantType.OSTERIA);
    return validDTO;
  }

  @Test
  public void testRestaurantCreation() throws Exception {
    RestaurantDTO validRequest = createTestDTO();
    mockMvc
        .perform(
            post("/restaurant/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", testToken)
                .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testGetRestaurant() throws Exception {
    Long id = 1L;
    RestaurantDTO restaurantDTO = createTestDTO();
    when(restaurantService.getRestaurantById(id)).thenReturn(restaurantDTO);
    mockMvc
        .perform(
            get("/restaurant/restaurant/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", testToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value(restaurantDTO.getName()))
        .andExpect(jsonPath("$.type").value(restaurantDTO.getType().toString()))
        .andExpect(jsonPath("$.url").value(restaurantDTO.getUrl()));
  }

  @Test
  public void testRestaurantNotFound() throws Exception {
    Long id = 2L;
    when(restaurantService.getRestaurantById(id)).thenThrow(new NoSuchElementException());
    mockMvc
        .perform(
            get("/restaurant/restaurant/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", testToken))
        .andExpect(status().isNotFound());
  }
}
