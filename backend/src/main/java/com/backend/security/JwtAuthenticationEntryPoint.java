package com.backend.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Questa classe gestisce l'errore di autenticazione e invia una risposta HTTP
 * con codice di stato 401 (Unauthorized).
 * Entra in gioco quando un utente non autenticato tenta di accedere a una
 * risorsa protetta.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * Invia una risposta HTTP con codice di stato 401 (Unauthorized) quando si
   * verifica un errore di autenticazione.
   *
   * @param request       La HttpServletRequest rappresenta la richiesta HTTP
   *                      inviata dall'utente.
   * @param response      La HttpServletResponse rappresenta la risposta HTTP che
   *                      verrà inviata all'utente con il codice di stato 401
   *                      (Unauthorized) e un messaggio di errore.
   * @param authException L'oggetto AuthenticationException rappresenta
   *                      l'eccezione di autenticazione che ha causato l'errore.
   * @throws IOException      Se si verifica un errore durante l'invio della
   *                          risposta HTTP.
   * @throws ServletException Se si verifica un errore durante l'invio della
   *                          risposta HTTP.
   */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException) throws IOException, ServletException {

    String errorMessage = "Errore di autenticazione generico"; // authException.getMessage() è più dettagliato
    if (authException != null) {
      errorMessage = authException.getMessage();
    }
    String jsonMessage = String.format("{\"error\": \"%s\"}", errorMessage);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write(jsonMessage);
  }
}