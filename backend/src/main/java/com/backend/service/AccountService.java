package com.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.model.DTO.request.AccountRegistrationDTO;
import com.backend.model.DTO.response.AccountResponseDTO;

@Service
public interface AccountService {

  List<AccountResponseDTO> getAllAccounts();

  Optional<AccountResponseDTO> getAccountById(Long id);

  AccountResponseDTO createAccount(AccountRegistrationDTO account);

  Optional<Account> updateAccount(Long id, Account accountDetails);

  boolean deleteAccount(Long id);

  AccountResponseDTO convertToDTO(Account account);
}
