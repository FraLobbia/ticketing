//package com.authentication.services;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import com.authentication.models.entities.Account;
//import com.authentication.models.entities.CustomUserDetails;
//import com.authentication.repositories.AccountRepository;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//  @Autowired
//  private AccountRepository accountRepository;
//
//  /**
//   * Carica un account dal database in base all'email fornita.
//   * 
//   * @param email L'email dell'account.
//   * @return Un oggetto UserDetails che rappresenta l'account.
//   * @throws UsernameNotFoundException Se l'account non è presente nel database.
//   */
//  public Account loadUserByEmail(String email) throws UsernameNotFoundException {
//    Account account = accountRepository.findByEmail(email)
//        .orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + email));
//    return account;
//  }
//  
//  public Account verifyAccount(String email, String password) throws UsernameNotFoundException {
//	Account account = accountRepository.findByEmailAndPassword(email, password)
//		.orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + email));
//	return account;
//  }
//  
//  public boolean verifyCredentials(String email, String password) {
//	  return accountRepository.findByEmailAndPassword(email, password).isPresent();
//  }
//
//  @Override
//  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//    // necessario questo redirect per via del funzionamento di Spring Security che
//    // predilige questo metodo nonostante venga chiamato loadUserByEmail
//    return loadUserByEmail(username);
//  }
//}
