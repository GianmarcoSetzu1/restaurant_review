package com.restaurantreview.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserRegisterRequest {
  @NotBlank private String username;
  @NotBlank @Email private String email;
  @NotBlank private String password;
}
