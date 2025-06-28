package com.restaurantreview.review_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.restaurantreview.review_service.dto.ReviewCreationRequest;
import com.restaurantreview.review_service.dto.ReviewResponse;
import com.restaurantreview.review_service.dto.ReviewsResponse;
import com.restaurantreview.review_service.model.Review;
import com.restaurantreview.review_service.repository.ReviewRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
public class ReviewServiceTest {

  @Mock JwtService jwtService;

  @Mock private ReviewRepository reviewRepository;

  @InjectMocks private ReviewService reviewService;

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
  public void getReviews_Success() {
    Long testUserId = 1L;
    List<Review> reviewList = new ArrayList<>();
    reviewList.add(new Review(1L, testUserId, 1L, 1.0F, "Scarso", LocalDateTime.now()));
    reviewList.add(new Review(2L, testUserId, 2L, 6.0F, "Sufficiente", LocalDateTime.now()));
    reviewList.add(new Review(3L, testUserId, 3L, 10.0F, "Eccellente", LocalDateTime.now()));

    var pageNumber = 0;
    var pageSize = 10;
    PageRequest pageable = PageRequest.of(pageNumber, pageSize);

    when(reviewRepository.findAll(pageable))
        .thenReturn(new PageImpl<>(reviewList, pageable, reviewList.size()));

    ReviewsResponse result = reviewService.findReviews(pageable);

    List<ReviewResponse> expected =
        reviewList.stream().map(review -> new ReviewResponse(review, false)).toList();
    assertEquals(expected, result.getContent());
    verify(reviewRepository, times(1)).findAll(pageable);
  }
}
