package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.exception.AuthHeaderParsingException;
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

  private Jws<Claims> validateToken(String token) throws JwtException {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
  }

  private Claims extractAllClaims(String token) {
    return validateToken(token).getPayload();
  }

  public void verifyAuth(String authHeader) {
    String bearerType = "Bearer ";
    if (authHeader != null && authHeader.startsWith(bearerType)) {
      String token = authHeader.substring(bearerType.length());
      try {
        validateToken(token);
      } catch (ExpiredJwtException e) {
        throw new RuntimeException("Token expired", e);
      } catch (JwtException e) {
        throw new RuntimeException("Invalid token", e);
      }
    } else {
      throw new AuthHeaderParsingException("Authentication header parsing failed");
    }
  }

  public Long extractUserId(String authHeader) {
    String bearerType = "Bearer ";
    if (authHeader != null && authHeader.startsWith(bearerType)) {
      String token = authHeader.substring(bearerType.length());
      try {
        Claims claims = extractAllClaims(token);
        return Long.valueOf(claims.getSubject());
      } catch (ExpiredJwtException e) {
        throw new RuntimeException("Token expired", e);
      } catch (JwtException e) {
        throw new RuntimeException("Invalid token", e);
      }
    } else {
      throw new AuthHeaderParsingException("Authentication header parsing failed");
    }
  }
}
