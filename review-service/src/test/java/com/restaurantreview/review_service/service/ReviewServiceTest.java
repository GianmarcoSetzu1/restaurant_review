package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        Long testUserId = 1L;
        ReviewCreationRequest request = new ReviewCreationRequest(testUserId, 1.0F, "Scarso");
        Review result = reviewService.createReview(testUserId, request);

        assertNotNull(result);
        assertEquals(testUserId, result.getUserId());
        assertEquals(request.getRestaurantId(), result.getRestaurantId());
        assertEquals(request.getRating(), result.getRating());
        assertEquals(request.getComment(), result.getComment());
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @Test
    public void getReviewsByUserId_Success() {
        Long testUserId = 1L;
        List<Review> reviewList = new ArrayList<>();
        reviewList.add(new Review(UUID.randomUUID(), testUserId, 1L, 1.0F, "Scarso", LocalDateTime.now()));
        reviewList.add(new Review(UUID.randomUUID(), testUserId, 2L, 6.0F, "Sufficiente", LocalDateTime.now()));
        reviewList.add(new Review(UUID.randomUUID(), testUserId, 3L, 10.0F, "Eccellente", LocalDateTime.now()));

        when(reviewRepository.findByUserId(testUserId)).thenReturn(reviewList);
        List<Review> result = reviewService.findByUserId(testUserId);

        assertEquals(reviewList, result);
        verify(reviewRepository, times(1)).findByUserId(testUserId);

    }
}
