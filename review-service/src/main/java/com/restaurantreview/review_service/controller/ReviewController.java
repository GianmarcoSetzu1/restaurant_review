package com.restaurantreview.review_service.controller;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.dto.ReviewsResponse;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.service.JwtService;
import com.restaurantreview.review_service.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

  @Autowired ReviewService reviewService;

  @Autowired private JwtService jwtService;

  @GetMapping("/")
  public ResponseEntity<ReviewsResponse> getUserReviews(
      @RequestHeader("Authorization") String authHeader,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int pageSize) {
    try {
      jwtService.verifyAuth(authHeader);
      Pageable pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
      Page<Review> reviewPage = reviewService.findReviews(pageable);
      ReviewsResponse response = getReviewsResponse(reviewPage);

      return ResponseEntity.ok(response);
    } catch (Exception e) {
      return ResponseEntity.status(401).build();
    }
  }

  private ReviewsResponse getReviewsResponse(Page<Review> page) {
    ReviewsResponse response = new ReviewsResponse();
    response.setContent(page.getContent());
    response.setPageNumber(page.getNumber());
    response.setTotalItems(page.getTotalElements());
    response.setTotalPages(page.getTotalPages());
    return response;
  }

  @PostMapping("/create")
  public ResponseEntity<?> createReview(
      @RequestHeader("Authorization") String authHeader,
      @Valid @RequestBody ReviewCreationRequest reviewCreationRequest) {
    try {
      Long userId = jwtService.extractUserId(authHeader);
      reviewService.createReview(userId, reviewCreationRequest);
      return new ResponseEntity<>(HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }
}
