package com.restaurantreview.review_service.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class JwtServiceTest {
  private final String secret = "mysecretkeyjwt1234567890abcdefghz";
  private JwtService jwtService;

  @BeforeEach
  void setUp() {
    jwtService = new JwtService(secret);
  }

  @Test
  public void extractUserId_Success() {
    String token =
        Jwts.builder()
            .subject("1")
            .expiration((Date.from(Instant.now().plus(1, ChronoUnit.HOURS))))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
            .compact();

    String authHeader = "Bearer " + token;
    Long result = jwtService.extractUserId(authHeader);
    assertEquals(1L, result);
  }

  @Test
  public void extractUserId_ParseFail() {
    String invalidAuthHeader = "invalid.token.here";
    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> jwtService.extractUserId(invalidAuthHeader));

    assertEquals("Authentication header parsing failed", ex.getMessage());
  }

  @Test
  public void extractUserId_Invalid() {
    String invalidAuthHeader = "Bearer invalid.token.here";
    RuntimeException ex =
        assertThrows(RuntimeException.class, () -> jwtService.extractUserId(invalidAuthHeader));

    assertEquals("Invalid token", ex.getMessage());
  }
}
