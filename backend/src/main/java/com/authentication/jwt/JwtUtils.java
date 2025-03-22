package com.authentication.jwt;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.app.service.AccountService;
import com.authentication.models.entities.Account;
import com.authentication.models.entities.CustomUserDetails;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Service
public abstract class JwtUtils {

	@Autowired
	private Environment env;
	
	@Autowired
	private AccountService accountService;

	/**
	 * Genera un token JWT per l'utente autenticato.
	 */
	public String generateJwtToken(String email, String password) {
		// da cambiare sta oscenità usando account service
		CustomUserDetails user = accountService.verifyAccount(email, password);
		Long idAccount = user.getId();
		Date expireDate = new Date(System.currentTimeMillis() + getJwtExpirationDate());
		
		// @formatter:off
			return Jwts.builder()
					.subject(email)
					.issuedAt(new Date())
					.claim("idAccount", idAccount)
					.claim("name", user.getName())
					.claim("surname", user.getSurname())
					.claim("roles", user.getAuthorities().stream()
											.map(GrantedAuthority::getAuthority)
											.toList())
					.expiration(expireDate)
					.signWith(getSigningKey())
					.compact();
			// @formatter:on
	}

	/**
	 * @param token
	 * @return Il subject del token JWT.
	 */
	public String extractEmailFromToken(String token) {
		return Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token).getPayload()
				.getSubject();

	}

	/**
	 * Verifica se un token JWT è valido.
	 * 
	 * @param token Il token JWT da validare.
	 * @return true se il token è valido, false altrimenti.
	 */
	public boolean isJwtTokenValid(String token) {
		try {
			((JwtParserBuilder) Jwts.parser().verifyWith((SecretKey) getSigningKey())).build().parseSignedClaims(token);

			return true;
		} catch (JwtException | IllegalArgumentException e) {
			// Qui puoi gestire le eccezioni specifiche per token non validi
			System.out.println("Errore di validazione token: " + e.getMessage());
			return false;
		}
	}

	private Key getSigningKey() {
		byte[] keyBytes = getJwtSecret().getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * @return La chiave segreta per JWT dalle properties.
	 */
	private String getJwtSecret() {
		String secret = env.getProperty("jwt.secret");
		if (secret == null) {
			throw new RuntimeException(
					"Non è stato possibile recuperare la chiave segreta per JWT dalle properties. Chiave cercata: jwt.secret");
		}
		return env.getProperty("jwt.secret");
	}

	/**
	 * @return La data di scadenza del token JWT dalle properties.
	 */
	private long getJwtExpirationDate() {
		String expiration = env.getProperty("jwt.expirationMs");
		if (expiration == null) {
			throw new RuntimeException(
					"Non è stato possibile recuperare la durata di validità del token JWT dalle properties. Chiave cercata: jwt.expirationMs");
		}
		return Long.parseLong(expiration);
	}
}
