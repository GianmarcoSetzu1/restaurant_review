package com.restaurantreview.review_service.repository;

import com.restaurantreview.review_service.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {}
