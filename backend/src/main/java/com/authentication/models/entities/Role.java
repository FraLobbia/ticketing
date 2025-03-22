package com.authentication.models.entities;

import jakarta.persistence.*;
import lombok.Data;

import org.springframework.security.core.GrantedAuthority;

import com.authentication.models.enums.RoleEnum;

@Entity
@Data
@Table(name = "ROLES")
public class Role implements GrantedAuthority {

  private static final long serialVersionUID = -8047497620617344134L;

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