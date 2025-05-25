package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.repository.ReviewRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;


@Service
public class ReviewService {

    private static final Logger logger = LoggerFactory.getLogger(ReviewService.class);

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Long userId, ReviewCreationRequest request) {
        Review newReview = new Review();
        newReview.setUserId(userId);
        newReview.setRestaurantId(request.getRestaurantId());
        newReview.setRating(request.getRating());
        newReview.setComment(request.getComment());
        reviewRepository.save(newReview);

        return newReview;
    }

    public Page<Review> findByUserId(Long userId, Pageable pageable) {
        logger.info("Trying to get user reviews for id {} ", userId);
        return reviewRepository.findByUserId(userId, pageable);
    }

}
