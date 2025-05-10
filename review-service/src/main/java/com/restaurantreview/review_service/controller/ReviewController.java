package com.restaurantreview.review_service.controller;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.service.JwtService;
import com.restaurantreview.review_service.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    ReviewService reviewService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/create")
    public ResponseEntity<?> createReview(@Valid @RequestBody ReviewCreationRequest reviewCreationRequest) {
        try {
            Review newReview = reviewService.createReview(reviewCreationRequest);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/all-reviews")
    public ResponseEntity<List<Review>> getUserReviews(@RequestHeader("Authorization") String authHeader) {
        try {
            Long userId = jwtService.extractUserId(authHeader);
            List<Review> reviews = reviewService.getReviewsByUserId(userId);
            return ResponseEntity.ok(reviews);
        } catch (Exception e) {
            return ResponseEntity.status(401).build();
        }
    }

}
