package com.restaurantreview.review_service.dto;

import com.restaurantreview.review_service.model.Review;
import lombok.Data;

import java.util.List;

@Data
public class ReviewsResponse {
    List<Review> content;
    Integer pageNumber;
    Long totalItems;
    Integer totalPages;
}
