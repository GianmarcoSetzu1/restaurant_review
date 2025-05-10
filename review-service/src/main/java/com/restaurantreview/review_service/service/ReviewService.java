package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(ReviewCreationRequest request) {
        Review newReview = new Review();
        newReview.setUserId(request.getUserId());
        newReview.setRestaurantId(request.getRestaurantId());
        newReview.setRating(request.getRating());
        newReview.setComment(request.getComment());
        reviewRepository.save(newReview);

        //TODO: Manage exceptions for null or bad formed cases

        return newReview;
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.getReviewsByUserId(userId);
    }

}
