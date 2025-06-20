package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.exception.UserNotOwnerException;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.repository.ReviewRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

  @Autowired private ReviewRepository reviewRepository;

  public Review createReview(Long userId, ReviewCreationRequest request) {
    Review newReview = new Review();
    newReview.setUserId(userId);
    newReview.setRestaurantId(request.getRestaurantId());
    newReview.setRating(request.getRating());
    newReview.setComment(request.getComment());
    reviewRepository.save(newReview);

    return newReview;
  }

  public void deleteReview(Long userId, Long reviewId)
      throws NoSuchElementException {
    Review foundReview = reviewRepository.findById(reviewId).orElseThrow();
    if (!foundReview.getUserId().equals(userId)) throw new UserNotOwnerException();
    reviewRepository.delete(foundReview);
  }

  public Page<Review> findReviews(Pageable pageable) {
    return reviewRepository.findAll(pageable);
  }
}
