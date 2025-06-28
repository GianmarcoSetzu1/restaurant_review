package com.restaurantreview.review_service.dto;

import com.restaurantreview.review_service.model.Review;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ReviewResponse {
  private Review review;
  private boolean deletable;
}
