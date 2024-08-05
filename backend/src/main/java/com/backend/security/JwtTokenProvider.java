package com.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtTokenProvider {

  private String jwtSecret = System.getenv("JWT_SECRET");

  private long jwtExpirationDate = 3600000; // 1h = 3600s e 3600*1000 = 3600000 milliseconds

  public String generateToken(Authentication authentication) {

    String username = authentication.getName();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

    return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(currentDate)
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS256, getSigningKey())
        .compact();
  }

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  // Estrae il nome utente dal token JWT
  public String getUsernameFromToken(String token) {
    return Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody()
        .getSubject();

  }

  // Valida il token JWT
  public boolean validateToken(String token) {
    try {
      Jwts.parser()
          .setSigningKey(getSigningKey())
          .parseClaimsJws(token);
      return true;
    } catch (Exception e) {
      // Qui puoi gestire le eccezioni specifiche per token non validi
      return false;
    }
  }
}
