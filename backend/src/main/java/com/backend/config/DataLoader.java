package com.backend.config;

import com.backend.model.Enum.RoleEnum;
import com.backend.model.Role;
import com.backend.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class DataLoader {

  @Autowired
  private RoleRepository roleRepository;

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
