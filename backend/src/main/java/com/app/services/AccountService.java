package com.app.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.authentication.models.dto.AccountDTO;
import com.authentication.models.dto.RegistrationDTO;
import com.authentication.models.entities.Account;

@Service
public interface AccountService {

  List<AccountDTO> getAllAccounts();

  Optional<AccountDTO> getAccountById(Long id);

//  AccountResponseDTO createAccount(RegistrationRequestDTO account);

  Optional<Account> updateAccount(Long id, Account accountDetails);

  boolean deleteAccount(Long id);

  AccountDTO convertToDTO(Account account);
}
