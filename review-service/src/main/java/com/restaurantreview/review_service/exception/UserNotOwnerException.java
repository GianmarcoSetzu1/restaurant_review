package com.restaurantreview.review_service.exception;

public class UserNotOwnerException extends RuntimeException {
  public UserNotOwnerException() {
    super("User is not owner of the review.");
  }
}
