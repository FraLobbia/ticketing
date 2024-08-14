package com.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.backend.model.DTO.LoginDTO;
import com.backend.security.JwtTokenProvider;
import com.backend.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  /**
   * Login
   * 
   * @param loginDto
   * @return
   */
  @Override
  public String login(LoginDTO loginDto) {

    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
        loginDto.getEmail(),
        loginDto.getPassword()));

    // Ottieni i dettagli dell'utente (UserDetails)
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // // Crea un CustomAuthenticationToken con l'ID dell'utente
    // AuthenticationToken customAuthentication = new AuthenticationToken(
    // userDetails.getUsername(),
    // userDetails.getPassword(),
    // userDetails.getAuthorities(),
    // userDetails.getId());
    /*
     * 02 - SecurityContextHolder is used to allows the rest of the application to
     * know
     * that the user is authenticated and can use user data from Authentication
     * object
     */
    SecurityContextHolder.getContext().setAuthentication(authentication);

    // 03 - Generate the token based on username and secret key
    String token = jwtTokenProvider.generateToken(authentication);

    // 04 - Return the token to controller
    return token;
  }

}
