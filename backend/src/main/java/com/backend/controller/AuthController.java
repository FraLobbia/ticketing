package com.backend.controller;

import com.backend.model.AuthRequest;
import com.backend.model.AuthResponse;
import com.backend.security.JwtUtil;
import com.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtUtil jwtUtil;

  @Autowired
  private UserService userService;

  @PostMapping("/login")
  public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) throws Exception {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
    } catch (Exception e) {
      throw new Exception("Incorrect email or password", e);
    }

    final UserDetails userDetails = userService.loadUserByUsername(authRequest.getEmail());
    final String jwt = jwtUtil.generateToken(userDetails.getUsername());

    return ResponseEntity.ok(new AuthResponse(jwt));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@RequestBody AuthRequest authRequest) {
    userService.saveUser(authRequest);
    return ResponseEntity.status(201).build();
  }
}