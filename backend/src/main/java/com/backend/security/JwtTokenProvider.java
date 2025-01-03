package com.backend.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.backend.model.CustomUserDetails;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenProvider {

  private final String jwtSecret;

  /**
   * Costruttore della classe JwtTokenProvider.
   * 
   * Inizializza la variabile jwtSecret con il valore della variabile d'ambiente
   * JWT_SECRET.
   * Se la variabile d'ambiente JWT_SECRET non è impostata o è vuota, viene
   * lanciata un'eccezione.
   * 
   * @throws IllegalStateException Se la variabile d'ambiente JWT_SECRET non è
   *                               impostata o è vuota.
   */
  public JwtTokenProvider() {
    Dotenv dotenv = Dotenv.load();
    jwtSecret = dotenv.get("JWT_SECRET");
    if (this.jwtSecret == null || this.jwtSecret.isEmpty()) {
      throw new IllegalStateException("JWT_SECRET environment variable is not set or is empty");
    }
  }

  private final long jwtExpirationDate = 3600000 * 24 * 7; // 1h = 3600s e 3600*1000 = 3600000 milliseconds

  /**
   * Genera un token JWT per l'utente autenticato.
   * 
   * @param authentication L'oggetto Authentication dell'utente autenticato è un
   *                       oggetto che rappresenta
   *                       l'identità dell'utente autenticato.
   *                       non si usa l'oggetto Account, ma l'oggetto UserDetails
   *                       perché è più generico e può rappresentare qualsiasi
   *                       utente autenticato.
   * @return Il token JWT generato.
   */
  public String generateToken(Authentication authentication) {

    // Estraggo l'oggetto UserDetails dall'oggetto Authentication
    UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();

    // converto l'oggetto UserDetails in un oggetto CustomUserDetails per ottenere i
    // metodi personalizzati come getId() e getEmail()
    CustomUserDetails customUserDetails = (CustomUserDetails) userPrincipal;

    Long idAccount = customUserDetails.getId();
    String email = customUserDetails.getEmail();
    Date currentDate = new Date();
    Date expireDate = new Date(currentDate.getTime() + jwtExpirationDate);

    return Jwts.builder()
        .subject(email)
        .issuedAt(currentDate)
        .claim("idAccount", idAccount)
        .claim("roles", "todo")
        .expiration(expireDate)
        .signWith(getSigningKey())
        .compact();
  }

  /**
   * @param token
   * @return Il subject del token JWT.
   */
  public String getUsernameFromToken(String token) {
    return Jwts.parser()
        .verifyWith((SecretKey) getSigningKey())
        .build().parseSignedClaims(token)
        .getPayload().getSubject();

  }

  /**
   * Verifica se un token JWT è valido.
   * 
   * @param token Il token JWT da validare.
   * @return true se il token è valido, false altrimenti.
   */
  public boolean validateToken(String token) {
    try {
      ((JwtParserBuilder) Jwts.parser()
          .verifyWith((SecretKey) getSigningKey()))
          .build()
          .parseSignedClaims(token);

      return true;
    } catch (JwtException | IllegalArgumentException e) {
      // Qui puoi gestire le eccezioni specifiche per token non validi
      System.out.println("Errore di validazione token: " + e.getMessage());
      return false;
    }
  }

  /**
   * Restituisce la chiave di firma per JWT.
   * hmacShaKeyFor() restituisce una chiave HMAC (Hash-based Message
   * Authentication Code) per la firma del token JWT.
   * La chiave HMAC è una funzione hash crittografica che accetta due argomenti:
   * una chiave segreta e un messaggio da autenticare.
   * 
   * @return La chiave di firma per JWT.
   */
  private Key getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

}
