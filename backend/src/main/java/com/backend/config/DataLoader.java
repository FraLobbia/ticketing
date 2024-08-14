package com.backend.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.backend.model.Enum.RoleEnum;
import com.backend.model.Role;
import com.backend.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

/**
 * Questa classe si occupa di caricare i dati iniziali nel database.
 */
@Component
public class DataLoader {

  @Autowired
  private RoleRepository roleRepository;

  /**
   * Carica i ruoli nel database se non sono già presenti.
   */
  @PostConstruct
  public void loadRoles() {
    if (roleRepository.count() == 0) {
      Role adminRole = new Role();
      adminRole.setName(RoleEnum.ROLE_ADMIN);
      Role userRole = new Role();
      userRole.setName(RoleEnum.ROLE_USER);
      roleRepository.saveAll(Arrays.asList(adminRole, userRole));
    }
  }
}
