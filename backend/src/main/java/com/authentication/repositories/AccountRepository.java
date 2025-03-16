package com.authentication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.authentication.models.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  Optional<Account> findByEmail(String email);
  
  Boolean existsByEmail(String email);
}
