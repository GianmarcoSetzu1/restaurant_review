package com.restaurantreview.user_service.controller;

import com.restaurantreview.user_service.dto.UserDTO;
import com.restaurantreview.user_service.dto.UserLoginRequest;
import com.restaurantreview.user_service.dto.UserLoginResponse;
import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.exception.EmailAlreadyExistsException;
import com.restaurantreview.user_service.exception.UserNotRegisteredException;
import com.restaurantreview.user_service.service.JwtService;
import com.restaurantreview.user_service.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        try {
            UserDTO registeredUser = userService.registerUser(userRegisterRequest);
            return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
        } catch (EmailAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        try {
            UserDTO loggedUser = userService.loginUser(userLoginRequest);
            String jwtToken = jwtService.generateToken(loggedUser.getEmail());
            return new ResponseEntity<>(new UserLoginResponse(jwtToken, loggedUser), HttpStatus.OK);
        } catch (UserNotRegisteredException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
