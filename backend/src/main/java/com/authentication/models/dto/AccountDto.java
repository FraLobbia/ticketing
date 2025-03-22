package com.authentication.models.dto;

//import java.util.Optional;
import java.util.Set;

import lombok.Data;

@Data
public class AccountDto {
  private Long id;
  private String name;
  private String surname;
  private String email;
  private Set<String> roles; 
  // private Optional<String> profilePictureUrl; // Potremmo voler esporre l'URL
  // dell'immagine profilo piuttosto che
  // l'array di
  // byte
}
