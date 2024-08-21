package com.backend.service;

import com.backend.model.DTO.request.LoginDTO;

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