package com.authentication.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.authentication.models.dto.LoginRequestDTO;
import com.authentication.models.dto.LoginResponseDTO;
import com.authentication.models.dto.RegistrationDTO;
import com.authentication.models.entities.CustomUserDetails;
import com.authentication.models.enums.AuthExceptionEnum;
import com.authentication.services.AuthService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private AuthService authService;

	@Override
	protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
			@NonNull FilterChain filterChain) throws ServletException, IOException {

		String path = request.getRequestURI();
		
		if ("/auth/login".equals(path) && "POST".equalsIgnoreCase(request.getMethod())) {
			handleLogin(request, response);
			return; // Interrompe la catena dei filtri dopo aver gestito la risposta con getOutputStream()
		}

		if ("/auth/register".equals(path) && "POST".equalsIgnoreCase(request.getMethod())) {
			handleRegister(request, response);
			return; // Interrompe la catena dei filtri dopo aver gestito la risposta con getOutputStream()
		}

		String token = getTokenFromRequest(request);

		if (StringUtils.hasText(token) && authService.isJwtTokenValid(token)) {

			// Assicurarsi che qui imposti i ruoli correttamente
			CustomUserDetails user = authService.getUserFromJwtToken(token);
			authService.setSecurityContext(user);
		}
		filterChain.doFilter(request, response);
	}

	/**
	 * Gestisce la richiesta di login
	 */
	private void handleLogin(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			LoginRequestDTO loginRequest = new ObjectMapper().readValue(request.getInputStream(),
					LoginRequestDTO.class);

			LoginResponseDTO dto = authService.login(loginRequest);

			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_OK);
			new ObjectMapper().writeValue(response.getOutputStream(), dto);
		} catch (Exception e) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			LoginResponseDTO loginResponse = new LoginResponseDTO(AuthExceptionEnum.INVALID_CREDENTIALS);
			new ObjectMapper().writeValue(response.getOutputStream(), loginResponse);
		}
	}

	/**
	 * Gestisce la richiesta di registrazione
	 */
	private void handleRegister(HttpServletRequest request, HttpServletResponse response) throws IOException {
		try {
			RegistrationDTO registrationRequest = new ObjectMapper().readValue(request.getInputStream(),
					RegistrationDTO.class);

			LoginResponseDTO dto = authService.register(registrationRequest);
			
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_CREATED);
			new ObjectMapper().writeValue(response.getOutputStream(), dto);
		} catch (Exception e) {
			response.setContentType("application/json");
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			LoginResponseDTO registerResponse = new LoginResponseDTO(AuthExceptionEnum.GENERIC_ERROR);
			new ObjectMapper().writeValue(response.getOutputStream(), registerResponse);
		}
	}

	/**
	 * Estrae il token JWT dall'header Authorization
	 */
	private String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}