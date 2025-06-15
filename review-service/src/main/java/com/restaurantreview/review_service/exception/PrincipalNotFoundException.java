package com.restaurantreview.review_service.exception;

public class PrincipalNotFoundException extends RuntimeException {
  public PrincipalNotFoundException() {
    super("User not found in the security context.");
  }
}
