package com.restaurantreview.review_service.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RatingValidator implements ConstraintValidator<ValidRating, Float> {

    @Override
    public boolean isValid(Float value, ConstraintValidatorContext context) {
        if (value == null) return true;
        return value % 0.5 == 0;
    }
}