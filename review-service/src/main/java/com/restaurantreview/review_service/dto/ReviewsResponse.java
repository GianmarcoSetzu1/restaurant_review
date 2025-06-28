package com.restaurantreview.review_service.dto;

import java.util.List;
import lombok.Data;

@Data
public class ReviewsResponse {
  List<ReviewResponse> content;
  Integer pageNumber;
  Long totalItems;
  Integer totalPages;
}
