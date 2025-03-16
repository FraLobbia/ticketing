package com.authentication.models.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RegistrationDTO {
  private String name;
  private String surname;
  private String email;
  private String password;
}
