package com.backend.repository;

import com.backend.model.Role;
import com.backend.model.Enum.RoleName;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleName name);

}
