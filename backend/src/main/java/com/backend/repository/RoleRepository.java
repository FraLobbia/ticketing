package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.Enum.RoleEnum;
import com.backend.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(RoleEnum name);

}
