package com.authentication.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authentication.services.AuthService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private AuthService authService;

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
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		String token = getTokenFromRequest(request);
		
		if (StringUtils.hasText(token) && authService.isJwtTokenValid(token)) {
			
			authService.setSecurityContext(token);
//			String email = jwtService.extractEmailFromToken(token);
//			UserDetails userDetails = userDetailsService.loadUserByEmail(email);
//
//			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
//					userDetails, null, userDetails.getAuthorities());
//
//			// Imposta i dettagli dell'utente autenticato nella richiesta.
//			authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//			// Imposta l'utente autenticato nel contesto di sicurezza.
//			SecurityContextHolder.getContext().setAuthentication(authenticationToken);
		}
		// Passa la richiesta e la risposta alla catena di filtri successiva.
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