package com.restaurantreview.review_service.repository;

import com.restaurantreview.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, UUID> {
    public Review getReviewById(UUID reviewId);

}
