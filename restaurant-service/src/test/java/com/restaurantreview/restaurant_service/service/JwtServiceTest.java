package com.restaurantreview.restaurant_service.service;

import static org.junit.jupiter.api.Assertions.assertThrows;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.junit.jupiter.api.Assertions;
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
  public void verifyAuth_Success() {
    String token =
        Jwts.builder()
            .subject("1")
            .expiration((Date.from(Instant.now().plus(1, ChronoUnit.HOURS))))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
            .compact();

    Assertions.assertDoesNotThrow(() -> jwtService.validateToken(token));
  }

  @Test
  public void expiredToken() {
    String expiredToken =
        Jwts.builder()
            .subject("1")
            .expiration((Date.from(Instant.now().minus(1, ChronoUnit.HOURS))))
            .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
            .compact();

    assertThrows(JwtException.class, () -> jwtService.validateToken(expiredToken));
  }

  @Test
  public void invalidToken() {
    String invalidAuthHeader = "invalid.token.here";
    assertThrows(JwtException.class, () -> jwtService.validateToken(invalidAuthHeader));
  }
}
