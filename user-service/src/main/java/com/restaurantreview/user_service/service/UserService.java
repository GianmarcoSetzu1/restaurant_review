package com.restaurantreview.user_service.service;

import com.restaurantreview.user_service.dto.UserLoginRequest;
import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.exception.EmailAlreadyExistsException;
import com.restaurantreview.user_service.exception.UserNotRegisteredException;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
  @Autowired private UserRepository userRepository;

  @Autowired private PasswordEncoder passwordEncoder;

  public UserAccount registerUser(UserRegisterRequest request) {
    userRepository
        .findByEmail(request.getEmail())
        .ifPresent(
            user -> {
              throw new EmailAlreadyExistsException("Email already registered");
            });

    UserAccount newUser = new UserAccount();
    newUser.setUsername(request.getUsername());
    newUser.setEmail(request.getEmail());
    String hashedPassword = passwordEncoder.encode(request.getPassword());
    newUser.setPassword(hashedPassword);

    userRepository.save(newUser);

    UserAccount response = new UserAccount();
    response.setEmail(newUser.getEmail());
    response.setUsername(newUser.getUsername());
    return response;
  }

  public UserAccount loginUser(UserLoginRequest request) {
    UserAccount foundUser = userRepository.findByEmail(request.getEmail()).orElseThrow();
    if (passwordEncoder.matches(request.getPassword(), foundUser.getPassword())) {
      UserAccount response = new UserAccount();
      response.setId(foundUser.getId());
      response.setEmail(foundUser.getEmail());
      response.setUsername(foundUser.getUsername());
      return response;
    } else {
      throw new UserNotRegisteredException("User not found");
    }
  }

  public UserAccount getUserById(Long id) throws JwtException, NoSuchElementException {
    UserAccount foundUser = userRepository.findById(id).orElseThrow();
    UserAccount user = new UserAccount();
    user.setEmail(foundUser.getEmail());
    user.setUsername(foundUser.getUsername());
    return user;
  }
}
