package com.backend.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.User;
import java.util.ArrayList;
import org.springframework.security.core.userdetails.UserDetails;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // Implementa il tuo metodo per caricare l'utente dal database
    return new User(username, "password", new ArrayList<>());
  }
}