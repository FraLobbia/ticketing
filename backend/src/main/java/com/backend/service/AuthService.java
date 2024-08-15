package com.backend.service;

import com.backend.model.DTO.LoginDTO;

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