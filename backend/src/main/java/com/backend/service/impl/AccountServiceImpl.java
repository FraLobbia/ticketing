package com.backend.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.model.DTO.AccountRegistrationDTO;
import com.backend.model.Enum.RoleEnum;
import com.backend.model.Role;
import com.backend.repository.AccountRepository;
import com.backend.repository.RoleRepository;
import com.backend.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

  @Autowired
  private AccountRepository accountRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public List<Account> getAllAccounts() {
    return accountRepository.findAll();
  }

  @Override
  public Optional<Account> getAccountById(Long id) {
    return accountRepository.findById(id);
  }

  @Override
  public Account createAccount(AccountRegistrationDTO accountRegistrationDTO) {
    String email = accountRegistrationDTO.getEmail();

    if (accountRepository.existsByEmail(email)) {
      throw new RuntimeException("Error: Email is already in use!");
    }

    Account account = new Account();
    account.setName(accountRegistrationDTO.getName());
    account.setSurname(accountRegistrationDTO.getSurname());
    account.setEmail(accountRegistrationDTO.getEmail());

    String encodedPassword = passwordEncoder.encode(accountRegistrationDTO.getPassword());
    account.setPassword(encodedPassword);

    Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Error: Role not found."));

    account.getRoles().add(userRole);
    try {
      accountRepository.save(account);
      return account;
    } catch (Exception e) {
      throw new RuntimeException("Error: Account not created.");
    }
  }

  @Override
  public Optional<Account> updateAccount(Long id, Account accountDetails) {
    Account account = accountRepository.findById(id).orElse(null);
    if (account == null) {
      return Optional.empty();
    }
    account.setName(accountDetails.getName());
    account.setSurname(accountDetails.getSurname());
    account.setEmail(accountDetails.getEmail());
    // account.setPassword(accountDetails.getPassword());

    accountRepository.save(account);

    return Optional.of(account);

  }

  @Override
  public boolean deleteAccount(Long id) {
    return accountRepository.findById(id).map(account -> {
      accountRepository.delete(account);
      return true;
    }).orElse(false);
  }
}
