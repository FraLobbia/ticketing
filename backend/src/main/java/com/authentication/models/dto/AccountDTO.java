package com.authentication.models.dto;

//import java.util.Optional;
import java.util.Set;

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
public class AccountDTO {
  private Long id;
  private String name;
  private String surname;
  private String email;
  private Set<String> roles; // Qui considero di esporre solo i nomi dei ruoli
  // private Optional<String> profilePictureUrl; // Potremmo voler esporre l'URL
  // dell'immagine profilo piuttosto che
  // l'array di
  // byte
}
