package com.restaurantreview.review_service.service;

import com.restaurantreview.review_service.exception.PrincipalNotFoundException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class JwtService {

  private final SecretKey key;

  JwtService(@Value("${jwt.secret}") String secret) {
    this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
  }

  public Jws<Claims> validateToken(String token) throws JwtException {
    return Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
  }

  public long extractUserId() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (!(authentication instanceof UsernamePasswordAuthenticationToken authenticationToken))
      throw new PrincipalNotFoundException();
    return Long.parseLong(String.valueOf(authenticationToken.getPrincipal()));
  }
}
