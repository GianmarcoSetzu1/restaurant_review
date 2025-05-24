package com.restaurantreview.review_service.exception;

public class AuthHeaderParsingException extends RuntimeException {
    public AuthHeaderParsingException(String message) {
        super(message);
    }
}