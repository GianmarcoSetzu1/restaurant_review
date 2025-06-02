package com.restaurantreview.user_service.exception;

public class AuthHeaderParsingException extends RuntimeException {
  public AuthHeaderParsingException(String message) {
    super(message);
  }
}
