package com.backend.controller;

import com.backend.model.DTO.AuthResponseDTO;
import com.backend.model.DTO.LoginDTO;
import com.backend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

  @Autowired
  private AuthService authService;

  // Build Login REST API
  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> login(@RequestBody LoginDTO loginDto) {

    // 01 - Receive the token from AuthService
    String token = authService.login(loginDto);

    // 02 - Set the token as a response using JwtAuthResponse Dto class
    AuthResponseDTO authResponseDto = new AuthResponseDTO();
    authResponseDto.setAccessToken(token);

    // 03 - Return the response to the user
    return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
  }
}