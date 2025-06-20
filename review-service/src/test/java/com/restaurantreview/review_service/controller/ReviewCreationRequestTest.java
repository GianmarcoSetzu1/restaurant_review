package com.restaurantreview.review_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.service.JwtService;
import com.restaurantreview.review_service.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class ReviewCreationRequestTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockitoBean private JwtService jwtService;

  @MockitoBean private ReviewService reviewService;

  @Test
  public void testValidRating() throws Exception {
    ReviewCreationRequest validRequest = new ReviewCreationRequest(1L, 8.0f, "Good restaurant");
    mockMvc
        .perform(
            post("/reviews/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validRequest)))
        .andExpect(status().isCreated());
  }

  @Test
  public void testInvalidRating() throws Exception {
    ReviewCreationRequest invalidRequest = new ReviewCreationRequest(1L, 2.3f, "Poor restaurant");
    mockMvc
        .perform(
            post("/reviews/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
        .andExpect(status().isBadRequest());
  }
}
