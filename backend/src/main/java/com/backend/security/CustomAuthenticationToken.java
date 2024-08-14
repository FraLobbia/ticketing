package com.backend.security;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class CustomAuthenticationToken extends UsernamePasswordAuthenticationToken {

  private Long userId;

  public CustomAuthenticationToken(Object principal, Object credentials, Long userId) {
    super(principal, credentials);
    this.userId = userId;
  }

  public CustomAuthenticationToken(Object principal, Object credentials,
      Collection<? extends GrantedAuthority> authorities, Long userId) {
    super(principal, credentials, authorities);
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }
}