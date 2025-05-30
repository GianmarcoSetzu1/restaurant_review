package com.restaurantreview.restaurant_service.exception;

public class AuthHeaderParsingException extends RuntimeException {
  public AuthHeaderParsingException(String message) {
    super(message);
  }
}
