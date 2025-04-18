package com.restaurantreview.review_service.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReviewCreationRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long restaurantId;
    @DecimalMin("1.0")
    private Integer rating;
    private String comment;
}
