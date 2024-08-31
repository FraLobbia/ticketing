package com.backend.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.backend.model.DTO.request.LoginDTO;
import com.backend.security.JwtTokenProvider;
import com.backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

  /**
   * Dependency Injections
   */
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * Costruttore
   */
  public AuthServiceImpl(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  /**
   * Login
   * Crea un oggetto UsernamePasswordAuthenticationToken con le credenziali dal
   * Dto e lo passa all'oggetto AuthenticationManager per
   * l'autenticazione.
   * Se l'autenticazione ha successo, l'oggetto Authentication viene impostato nel
   * contesto di sicurezza, quindi viene generato un token JWT.
   * 
   * @param loginDto
   * @return token JWT generato dal metodo {@link JwtTokenProvider#generateToken}
   */
  @Override
  public String login(LoginDTO loginDto) {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getEmail(),
        loginDto.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenProvider.generateToken(authentication);
    return token;
  }

}
