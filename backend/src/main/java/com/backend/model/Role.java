package com.backend.model;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import com.backend.model.Enum.RoleEnum;

@Entity
@Data
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private RoleEnum name;

  @Override
  public String getAuthority() {
    return name.toString();
  }
}