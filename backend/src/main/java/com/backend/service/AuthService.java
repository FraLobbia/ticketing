package com.backend.service;

import com.backend.model.DTO.LoginDTO;

public interface AuthService {
  String login(LoginDTO loginDto);
}