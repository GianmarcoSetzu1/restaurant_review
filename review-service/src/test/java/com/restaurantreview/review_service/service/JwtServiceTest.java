package com.restaurantreview.review_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtServiceTest {
  private final String secret = "mysecretkeyjwt1234567890abcdefghz";
  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService(secret);
  }

  @Test
  public void extractUserId_Success() {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(1L, null, Collections.emptyList());
    SecurityContextHolder.getContext().setAuthentication(authToken);

    Long result = jwtService.extractUserId();
    assertEquals(1L, result);
  }

  @Test
  public void extractUserId_ParseFail() {
    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(null, null, Collections.emptyList());
    SecurityContextHolder.getContext().setAuthentication(authToken);
    assertThrows(RuntimeException.class, () -> jwtService.extractUserId());
  }
}
