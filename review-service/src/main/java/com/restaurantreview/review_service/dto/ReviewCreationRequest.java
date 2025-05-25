package com.restaurantreview.review_service.dto;

import com.restaurantreview.review_service.validation.ValidRating;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCreationRequest {
  @NotNull private Long restaurantId;

  @DecimalMin("1.0")
  @DecimalMax("10.0")
  @ValidRating
  private Float rating;

  private String comment;
}
