package com.backend.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.github.cdimascio.dotenv.Dotenv;
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
    // jwtSecret =
    // "af60addca9ea3e3c099551e1b6576c9966dce0a33de879dd7e160f86dbd872ca236d6e9ee66fb6e30039fe7c345324a10f3d0741b0600fa7a45df4c6691eff4f4209767ed39f51e37717d8feecd5dd14fc34ebe619e6a29ae91d9ffe134cb5718bec0b3680d6ae7fc09e67763fe7c05d05d3ba69f47211163852633755b7f861132b0c98f8d7c1af9152d547408e676867a0a32fb525a4354180f5fb6b2dc23b5faa4155b8db63385f96259a90b6ee0e74a5b90a4f0f4fa96fafc296c64588b5c009b3829ae2e1d69a1cf7569b50a65fa553350495d18816f785f961c970c0a9cb9c8da25cc5e9fa4a3e9527a132d616b232d1ee21c3bf6dc8d9e3376e2e82c0";
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
    } catch (Exception e) {
      // Qui puoi gestire le eccezioni specifiche per token non validi
      System.out.println("Error: " + e.getMessage());
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

  // /**
  // * @return La chiave per la firma del token JWT.
  // */
  // private Key key() {
  // return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  // }

}
