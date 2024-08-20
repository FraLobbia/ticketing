package com.backend.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AccountRegistrationDTO {
  private String name;
  private String surname;
  private String email;
  private String password;
}
