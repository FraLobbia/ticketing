package com.backend.service;

import java.util.List;
import java.util.Optional;

import com.backend.model.Account;
import com.backend.model.DTO.AccountRegistrationDTO;

public interface AccountService {

  List<Account> getAllAccounts();

  Optional<Account> getAccountById(Long id);

  Account createAccount(AccountRegistrationDTO account);

  Optional<Account> updateAccount(Long id, Account accountDetails);

  boolean deleteAccount(Long id);
}
