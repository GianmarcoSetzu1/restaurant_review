package com.restaurantreview.user_service.controller;

import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserAccount> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            // Register the user by the service
            UserAccount registeredUser = userService.registerUser(userRegisterRequest);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED); // Return 201 (Created)
        } catch (Exception e) {
            // Error handling
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Return 400
        }
    }

    // Endpoint to get a user by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserAccount> getUserById(@PathVariable Long id) {
        UserAccount user = userService.getUserById(id);
        if (user != null) {
            return new ResponseEntity<>(user, HttpStatus.OK); // Return 200 OK
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Return 404 Not Found
        }
    }
}
