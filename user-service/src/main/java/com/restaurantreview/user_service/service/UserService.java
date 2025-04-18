package com.restaurantreview.user_service.service;

import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserAccount registerUser(UserRegisterRequest request) {
        // Check if the email is not registered yet
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("Email already registered");
                });

        // Create new User
        UserAccount newUser = new UserAccount();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());

        // Save user in the DB
        return userRepository.save(newUser);
    }

    public UserAccount getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            return null;
        }

    }
}
