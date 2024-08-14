package com.backend.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.backend.model.Account;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

  @Autowired
  private Account account;

  private Long id;
  private String email;
  private String password;

  private Collection<? extends GrantedAuthority> authorities;
  // Altri campi

  // Costruttore che accetta un oggetto Account
  public CustomUserDetails(Account account) {
    this.account = account;
    this.id = account.getId(); // Supponendo che Account abbia un campo ID
    this.email = account.getEmail();
    this.password = account.getPassword();
    this.authorities = account.getAuthorities(); // Se Account ha i ruoli
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return account.getRoles();
  }

  @Override
  public String getPassword() {

    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  public String getEmail() {
    return email;
  }

  // Implementazione degli altri metodi di UserDetails
}
