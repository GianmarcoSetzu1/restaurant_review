package com.restaurantreview.user_service.controller;

import com.restaurantreview.user_service.dto.UserDTO;
import com.restaurantreview.user_service.dto.UserLoginRequest;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.repository.UserRepository;
import com.restaurantreview.user_service.service.JwtService;
import com.restaurantreview.user_service.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class LoginTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }


    @Test
    public void testLogin_Success() throws Exception {

        UserLoginRequest request = new UserLoginRequest("john@example.com", "password123");
        UserAccount user = new UserAccount(1L, "john_doe", "john@example.com", "hashed_password", LocalDateTime.now());
        String token = "jwt-token";

        when(userService.loginUser(any(UserLoginRequest.class))).thenReturn(new UserDTO(user.getId(), user.getEmail(), user.getUsername()));
        when(jwtService.generateToken(any(Long.class))).thenReturn(token);

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        mockMvc.perform(post("/users/login")
                        .contentType("application/json")
                        .content(String.format("{\"email\": \"%s\", \"password\": \"%s\" }", request.getEmail(),  request.getPassword())))
                .andExpect(status().is(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.token").value(token))
                .andExpect(jsonPath("$.user.email").value(user.getEmail()))
                .andExpect(jsonPath("$.user.username").value(user.getUsername()));

        verify(userService, times(1)).loginUser(any(UserLoginRequest.class));
    }

    @Test
    public void testLogin_Failure_InvalidCredentials() throws Exception {
       UserLoginRequest request = new UserLoginRequest("john@example.com", "wrongpassword");

        // Simulate invalid login (user not found or password mismatch)
        mockMvc.perform(post("/api/users/login")
                        .contentType("application/json")
                        .content(String.format("{\"email\": \"%s\", \"password\": \"%s\" }", request.getEmail(),  request.getPassword())))
                .andExpect(status().isNotFound());
    }
}
