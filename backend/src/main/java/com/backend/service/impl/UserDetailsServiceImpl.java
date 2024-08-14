package com.backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.repository.AccountRepository;
import com.backend.security.CustomUserDetails;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private AccountRepository accountRepository;

  /**
   * Override del metodo loadUserByUsername per caricare un account tramite
   * l'email. L'interfaccia UserDetailsService richiede l'implementazione di
   * questo metodo.
   * 
   * @param email L'email dell'account.
   * @return Un oggetto UserDetails che rappresenta l'account.
   * @throws UsernameNotFoundException Se l'account non è presente nel database.
   */
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    Account account = accountRepository.findByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + email));
    return new CustomUserDetails(account);
  }
}
