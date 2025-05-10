package com.restaurantreview.user_service.service;

import com.restaurantreview.user_service.dto.UserDTO;
import com.restaurantreview.user_service.dto.UserLoginRequest;
import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.exception.EmailAlreadyExistsException;
import com.restaurantreview.user_service.exception.UserNotRegisteredException;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO registerUser(UserRegisterRequest request) {
        userRepository.findByEmail(request.getEmail())
                .ifPresent(user -> {
                    throw new EmailAlreadyExistsException("Email already registered");
                });

        UserAccount newUser = new UserAccount();
        newUser.setUsername(request.getUsername());
        newUser.setEmail(request.getEmail());

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        newUser.setPassword(hashedPassword);

        userRepository.save(newUser);

        return new UserDTO(newUser.getId(), newUser.getEmail(), newUser.getUsername());
    }

    public UserDTO loginUser(UserLoginRequest request) {
        Optional<UserAccount> foundUser = userRepository.findByEmail(request.getEmail());
        if ((foundUser.isPresent()) &&
            (passwordEncoder.matches(request.getPassword(), foundUser.get().getPassword()))) {
                return new UserDTO(foundUser.get().getId(), foundUser.get().getEmail(), foundUser.get().getUsername());
        } else {
            throw new UserNotRegisteredException("User not found");
        }
    }

}
