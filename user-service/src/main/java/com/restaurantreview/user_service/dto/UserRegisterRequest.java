package com.restaurantreview.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank
    String username;
    @NotBlank
    @Email
    String email;
    @NotBlank
    String password;
}
