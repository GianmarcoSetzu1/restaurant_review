package com.restaurantreview.review_service.service.cache;

import com.restaurantreview.review_service.model.Review;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CacheableInterface {
    @Cacheable(value = "reviews", key="{#userId}")
    public Page<Review> findByUserId(Long userId, Pageable pageable);
}
