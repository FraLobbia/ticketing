package com.backend.controller;

import com.backend.model.DTO.AuthResponseDTO;
import com.backend.model.DTO.LoginDTO;
import com.backend.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.backend.model.DTO.UserRegistrationDTO;
import com.backend.service.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private UserService userService;

  // Build Login REST API
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {

    // 01 - Receive the token from AuthService
    String token = authService.login(loginDto);

    // 02 - Set the token as a response using JwtAuthResponse Dto class
    AuthResponseDTO authResponseDto = new AuthResponseDTO();
    authResponseDto.setAccessToken(token);

    // 03 - Return the response to the user
    return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody UserRegistrationDTO registrationDto) {
    userService.registerUser(registrationDto);
    return ResponseEntity.ok("User registered successfully");
  }

}