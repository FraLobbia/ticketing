package com.backend.repository;

import com.backend.model.Role;
import com.backend.model.Enum.RoleEnum;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleEnum name);

}
