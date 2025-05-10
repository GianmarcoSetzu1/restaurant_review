package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    public void createReview_Success() {
        ReviewCreationRequest request = new ReviewCreationRequest(1L, 1L, 1.0F, "Scarso");
        Review result = reviewService.createReview(request);

        assertNotNull(result);
        assertEquals(request.getUserId(), result.getUserId());
        assertEquals(request.getRestaurantId(), result.getRestaurantId());
        assertEquals(request.getRating(), result.getRating());
        assertEquals(request.getComment(), result.getComment());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void getReviewsByUserId_Success() {
        //TODO: Write a test for getReviews
    }
}
