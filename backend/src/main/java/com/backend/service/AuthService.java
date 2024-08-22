package com.backend.service;

import org.springframework.stereotype.Service;

import com.backend.model.DTO.request.LoginDTO;

@Service
public interface AuthService {

  /**
   * Login
   * 
   * @see implementazione in AuthServiceImpl.java
   * @param loginDto
   * @return
   */
  String login(LoginDTO loginDto);
}