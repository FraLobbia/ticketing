package com.backend.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.DTO.request.AccountRegistrationDTO;
import com.backend.model.DTO.request.LoginDTO;
import com.backend.model.DTO.response.AccountResponseDTO;
import com.backend.model.DTO.response.AuthResponseDTO;
import com.backend.service.AccountService;
import com.backend.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  /**
   * Servizi iniettati
   */
  private final AuthService authService;

  private final AccountService accountService;

  /**
   * Costruttore
   */
  public AuthController(AuthService authService, AccountService accountService) {
    this.authService = authService;
    this.accountService = accountService;
  }

  /**
   * @param loginDto un oggetto {@link LoginDTO} contenente i dati necessari per
   *                 il login.
   * @return Un oggetto {@link ResponseEntity} contenente il token di
   *         autenticazione
   */
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {
    String token = authService.login(loginDto);
    AuthResponseDTO authResponseDto = new AuthResponseDTO();
    authResponseDto.setAccessToken(token);
    return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
  }

  /**
   * @param registrationDto Un oggetto {@link AccountRegistrationDTO} contenente i
   *                        dati necessari per registrare un nuovo account.
   * @return Un oggetto {@link ResponseEntity} contenente l'account creato o un
   *         errore HTTP.
   */
  @PostMapping("/register")
  public ResponseEntity<AccountResponseDTO> register(@RequestBody AccountRegistrationDTO registrationDto) {
    AccountResponseDTO createdAccount = accountService.createAccount(registrationDto);
    if (createdAccount == null) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
  }

}