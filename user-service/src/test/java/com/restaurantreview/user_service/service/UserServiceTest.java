package com.restaurantreview.user_service.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.restaurantreview.user_service.dto.UserRegisterRequest;
import com.restaurantreview.user_service.model.UserAccount;
import com.restaurantreview.user_service.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

  @Mock private UserRepository userRepository;

  @Mock private PasswordEncoder passwordEncoder;

  @Mock private JwtService jwtService;

  @InjectMocks private UserService userService;

  @Test
  public void testRegisterUser_Success() {
    UserRegisterRequest request =
        new UserRegisterRequest("Test", "test@example.com", "testpassword");
    UserAccount result = userService.registerUser(request);

    assertNotNull(result);
    assertEquals(request.getEmail(), result.getEmail());
    assertEquals(request.getUsername(), result.getUsername());
    verify(userRepository, times(1)).save(any(UserAccount.class));
  }

  @Test
  public void testRegisterUser_EmailAlreadyRegistered() {
    UserRegisterRequest request =
        new UserRegisterRequest("Test", "test@example.com", "testpassword");

    when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(new UserAccount()));
    RuntimeException exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              userService.registerUser(request);
            });

    assertEquals("Email already registered", exception.getMessage());
    verify(userRepository, times(0)).save(any(UserAccount.class));
  }

  @Test
  public void getUserByIdTest() {
    UserAccount user = new UserAccount();
    user.setId(1L);
    user.setEmail("test@example.com");
    user.setUsername("testuser");

    UserAccount foundUser = new UserAccount();
    foundUser.setId(user.getId());
    foundUser.setEmail(user.getEmail());
    foundUser.setUsername(user.getUsername());

    when(userRepository.findById(user.getId())).thenReturn(Optional.of(foundUser));
    UserAccount result = userService.getUserById(user.getId());

    assertEquals(result.getEmail(), user.getEmail());
    assertEquals(result.getUsername(), user.getUsername());
    verify(userRepository, times(1)).findById(any(Long.class));
  }
}
