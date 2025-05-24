package com.restaurantreview.review_service.controller;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.service.JwtService;
import com.restaurantreview.review_service.service.ReviewService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ExtendWith(MockitoExtension.class)
public class ReviewCreationRequestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private ReviewService reviewService;

    String testToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2ZWdldGFAbGliZXJvLY5NTU3ODJ9Okhyt4ZX9j2ntGJJ8NkdS6v8w4mC1hGC8";


    @Test
    public void testValidRating() throws Exception {
        ReviewCreationRequest validRequest = new ReviewCreationRequest(1L, 8.0f, "Good restaurant");
        mockMvc.perform(post("/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", testToken)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testInvalidRating() throws Exception {
        ReviewCreationRequest invalidRequest = new ReviewCreationRequest(1L, 2.3f, "Poor restaurant");
        mockMvc.perform(post("/reviews/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", testToken)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }
}
