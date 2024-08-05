package com.backend.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserRegistrationDTO {
  private String name;
  private String surname;
  private String email;
  private String password;
}
