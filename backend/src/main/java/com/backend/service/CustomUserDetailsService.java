package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.model.CustomUserDetails;
import com.backend.repository.AccountRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  /**
   * Carica un account dal database in base all'email fornita.
   * 
   * @param email L'email dell'account.
   * @return Un oggetto UserDetails che rappresenta l'account.
   * @throws UsernameNotFoundException Se l'account non è presente nel database.
   */
  public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + email));
    return new CustomUserDetails(account);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    // necessario questo redirect per via del funzionamento di Spring Security che
    // predilige questo metodo nonostante venga chiamato loadUserByEmail
    return loadUserByEmail(username);
  }
}
