package com.restaurantreview.user_service.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey key;
  private final long jwtExpirationInMs;

  public JwtService(
      @Value("${jwt.secret}") String secret, @Value("${jwt.expiration}") long jwtExpirationInMs) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    this.jwtExpirationInMs = jwtExpirationInMs;
  }

  public String generateToken(Long id) {
    var now = new Date();
    var expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return Jwts.builder()
        .subject(String.valueOf(id))
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(key)
        .compact();
  }

  public void validateToken(String token) throws JwtException {
    Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
  }
}
