package com.authentication.models.dto;

import com.authentication.models.enums.AuthExceptionEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
  private String accessToken;
  private String exceptionCode;
  private String message;
  
  public LoginResponseDTO(String accessToken) {
	this.accessToken = accessToken;
  }
  
  public LoginResponseDTO(AuthExceptionEnum exception) {
	  this.exceptionCode = exception.getCode();
	  this.message = exception.getMessage();
  }
}
