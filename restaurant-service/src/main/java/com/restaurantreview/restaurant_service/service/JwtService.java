package com.restaurantreview.restaurant_service.service;

import com.restaurantreview.restaurant_service.exception.AuthHeaderParsingException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey key;

  JwtService(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  private void validateToken(String token) throws JwtException {
    Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
  }

  public void verifyAuth(String authHeader) throws JwtException {
    String bearerType = "Bearer ";
    if (authHeader != null && authHeader.startsWith(bearerType)) {
      String token = authHeader.substring(bearerType.length());
      validateToken(token);
    } else {
      throw new AuthHeaderParsingException("Authentication header parsing failed");
    }
  }
}
