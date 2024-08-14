package com.backend.security;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final JwtTokenProvider jwtTokenProvider;

  private final UserDetailsService userDetailsService;

  // Costruttore della classe JwtAuthenticationFilter.
  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider, UserDetailsService userDetailsService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.userDetailsService = userDetailsService;
  }

  /**
   * Filtra le richieste HTTP per autenticare l'utente tramite il token JWT.
   * 
   * @param request     La richiesta HTTP.
   * @param response    La risposta HTTP.
   * @param filterChain Il chain di filtri che gestisce la richiesta HTTP.
   * @throws ServletException Se si verifica un'eccezione durante il processing
   *                          del filtro.
   * @throws IOException      Se si verifica un'eccezione di I/O durante il
   *                          processing del filtro.
   */
  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain) throws ServletException, IOException {
    String token = getTokenFromRequest(request);
    if (StringUtils.hasText(token) && jwtTokenProvider.validateToken(token)) {
      String username = jwtTokenProvider.getUsernameFromToken(token);
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          userDetails,
          null,
          userDetails.getAuthorities());
      authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Estrae il token dall'header Authorization della richiesta HTTP.
   *
   * @param request La richiesta HTTP da cui estrarre il token.
   * @return Il token estratto dalla richiesta HTTP o null se non è presente.
   */
  private String getTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7, bearerToken.length());
    }
    return null;
  }
}