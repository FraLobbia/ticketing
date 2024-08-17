package com.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.Account;
import com.backend.model.DTO.AccountResponseDTO;
import com.backend.model.DTO.AuthResponseDTO;
import com.backend.model.DTO.LoginDTO;
import com.backend.service.AccountService;
import com.backend.service.AuthService;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @Autowired
  private AuthService authService;

  @GetMapping
  public List<AccountResponseDTO> getAllAccounts() {
    return accountService.getAllAccounts();
  }

  /**
   * Get account by id
   * 
   * @param id
   * @return un oggetto {@link ResponseEntity} contenente l'account cercato o un
   *         errore HTTP.
   */
  @GetMapping("/{id}")
  public ResponseEntity<AccountResponseDTO> getAccountById(@PathVariable Long id) {
    return ResponseEntity.of(accountService.getAccountById(id));
  }

  /**
   * Update account
   * 
   * @param id
   * @param accountDetails
   * @return un oggetto {@link ResponseEntity} contenente l'account aggiornato o
   *         un
   *         errore HTTP.
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {

    // TODO: Implement the updateAccount method, QUESTO NON FUNZIONA

    Optional<Account> updatedAccount = accountService.updateAccount(id, accountDetails);

    if (updatedAccount == null) {
      return ResponseEntity.notFound().build();
    }

    // Crea un oggetto LoginDTO per passare i dettagli dell'account all'AuthService
    LoginDTO loginDto = new LoginDTO();
    loginDto.setEmail(updatedAccount.get().getEmail());
    loginDto.setPassword(updatedAccount.get().getPassword());

    // Ottieni il token JWT aggiornato
    String token = authService.login(loginDto);
    AuthResponseDTO authResponseDto = new AuthResponseDTO();
    authResponseDto.setAccessToken(token);

    // Crea una risposta combinata con i dettagli dell'account aggiornato e il token
    Map<String, Object> response = new HashMap<>();
    response.put("account", updatedAccount);
    response.put("auth", authResponseDto);

    return ResponseEntity.ok(response);
  }

  /**
   * Delete account
   * 
   * @param id
   * @return un oggetto {@link ResponseEntity} contenente un messaggio di successo
   *         o un errore HTTP.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
    if (accountService.deleteAccount(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
