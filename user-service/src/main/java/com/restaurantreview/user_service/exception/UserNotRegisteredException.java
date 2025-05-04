package com.restaurantreview.user_service.exception;

public class UserNotRegisteredException extends RuntimeException {
    public UserNotRegisteredException(String message) {
        super(message);
    }
}