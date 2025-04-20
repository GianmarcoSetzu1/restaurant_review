package com.restaurantreview.user_service.service;

import com.restaurantreview.user_service.dto.UserDTO;
import com.restaurantreview.user_service.dto.UserLoginRequest;
import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserAccount registerUser(UserRegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new RuntimeException("Email already registered");
                });

        UserAccount newUser = new UserAccount();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(request.getPassword());

        return userRepository.save(newUser);
    }

    public UserDTO loginUser(UserLoginRequest request) {
        Optional<UserAccount> foundUser = userRepository.findByEmail(request.getEmail());
        if ((foundUser.isPresent()) &&
            (request.getPassword().equals(foundUser.get().getPassword()))) {
                return new UserDTO(foundUser.get().getEmail(), foundUser.get().getUsername());
        } else {
            return null;
        }
    }

    public UserAccount getUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            return userRepository.findById(id).get();
        } else {
            return null;
        }

    }
}
