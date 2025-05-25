package com.restaurantreview.review_service.dto;

import com.restaurantreview.review_service.model.Review;
import java.util.List;
import lombok.Data;

@Data
public class ReviewsResponse {
  List<Review> content;
  Integer pageNumber;
  Long totalItems;
  Integer totalPages;
}
