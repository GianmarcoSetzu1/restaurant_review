package com.restaurantreview.review_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReviewCreationRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long restaurantId;
    @DecimalMin("1.0")
    private Float rating;
    private String comment;
}
