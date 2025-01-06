package com.backend.security;

import java.nio.file.Paths;
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
import lombok.extern.slf4j.Slf4j;

/**
 * Classe JwtTokenProvider responsabile della generazione e validazione dei
 * token JWT.
 * Utilizza una chiave segreta caricata da un file .env per firmare i token.
 * 
 * <p>
 * Metodi principali:
 * </p>
 * <ul>
 * <li>{@link #generateToken(Authentication)}: Genera un token JWT per un utente
 * autenticato.</li>
 * <li>{@link #getUsernameFromToken(String)}: Estrae il nome utente (subject) da
 * un token JWT.</li>
 * <li>{@link #validateToken(String)}: Verifica se un token JWT è valido.</li>
 * </ul>
 * 
 * <p>
 * Proprietà:
 * </p>
 * <ul>
 * <li>jwtSecret: La chiave segreta per la firma del token JWT.</li>
 * <li>jwtExpirationDate: La durata di validità del token JWT in
 * millisecondi.</li>
 * </ul>
 * 
 * <p>
 * Costruttore:
 * </p>
 * <ul>
 * <li>{@link #JwtTokenProvider()}: Carica la chiave segreta dal file .env.</li>
 * </ul>
 * 
 * <p>
 * Metodi privati:
 * </p>
 * <ul>
 * <li>{@link #loadJwtSecretFromEnv()}: Carica la chiave segreta per la firma
 * del token JWT dal file .env.</li>
 * <li>{@link #getSigningKey()}: Restituisce la chiave di firma per JWT.</li>
 * </ul>
 * 
 * <p>
 * Eccezioni:
 * </p>
 * <ul>
 * <li>{@link IllegalStateException}: Se il file .env non viene trovato o la
 * chiave JWT_SECRET non è impostata.</li>
 * </ul>
 * 
 * <p>
 * Dipendenze:
 * </p>
 * <ul>
 * <li>Dotenv: Per caricare le variabili d'ambiente dal file .env.</li>
 * <li>Jwts: Per creare e analizzare i token JWT.</li>
 * <li>Keys: Per generare la chiave di firma HMAC.</li>
 * </ul>
 */
@Slf4j
@Component
public class JwtTokenProvider {

  // Properties
  private final String jwtSecret;
  private final long jwtExpirationDate = 3600000 * 24 * 7; // 1h = 3600s e 3600*1000 = 3600000 milliseconds

  /**
   * Costruttore della classe JwtTokenProvider.
   */
  public JwtTokenProvider() {
    this.jwtSecret = loadJwtSecretFromEnv();
  }

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

  /**
   * Carica la chiave segreta per la firma del token JWT dal file .env.
   * Esegue 3 tentativi:
   * 1. Carica il file .env dalla directory corrente. (se si avvia il progetto con
   * docker-compose)
   * 2. Carica il file .env dalla directory superiore. (se si avvia il progetto
   * con mvn spring-boot:run)
   * 3. Se non trova il file .env, lancia un'eccezione.
   * 
   * @return String La chiave segreta per la firma del token JWT.
   */
  private String loadJwtSecretFromEnv() {
    Dotenv dotenv = null;

    // Primo tentativo: directory corrente
    try {
      dotenv = Dotenv.configure().load();
      log.info("Caricato file .env dalla directory corrente.");
    } catch (Exception e) {
      log.warn("File .env non trovato nella directory corrente. Tentativo con la directory superiore...");
    }

    // Secondo tentativo: directory superiore
    if (dotenv == null) {
      try {
        String parentDir = Paths.get(".").toAbsolutePath().normalize().getParent().toString();
        dotenv = Dotenv.configure().directory(parentDir).load();
        log.info("Caricato file .env dalla directory superiore: " + parentDir);
      } catch (Exception e) {
        log.error("File .env non trovato nella directory superiore.");
      }
    }

    if (dotenv != null) {
      String jwtSecret = dotenv.get("JWT_SECRET");
      if (jwtSecret == null || jwtSecret.isEmpty()) {
        throw new IllegalStateException("JWT_SECRET non è impostata nel file .env.");
      }
      return jwtSecret;
    } else {
      throw new IllegalStateException(
          "File .env non trovato né nella directory corrente né in quella superiore. Creare un file .env con property JWT_SECRET.");
    }
  }
}
