package com.restaurantreview.review_service.repository;

import com.restaurantreview.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> getReviewsByUserId(Long reviewId);

}
