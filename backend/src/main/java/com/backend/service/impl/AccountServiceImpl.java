package com.backend.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.backend.model.Account;
import com.backend.model.DTO.AccountRegistrationDTO;
import com.backend.model.DTO.AccountResponseDTO;
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

  /**
   * Ottiene tutti gli account dal database.
   *
   * @return una lista di oggetti Account {@link Account}.
   */
  @Override
  public List<AccountResponseDTO> getAllAccounts() {
    List<Account> accounts = accountRepository.findAll();
    return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
  }

  /**
   * Ottiene un account dal database in base all'ID fornito e lo mappa in un
   * oggetto AccountResponseDTO.
   *
   * @param id L'ID dell'account da ottenere.
   * @return un oggetto AccountResponseDTO {@link AccountResponseDTO} contenente
   *         l'account richiesto o vuoto se non esiste.
   */
  @Override
  public Optional<AccountResponseDTO> getAccountById(Long id) {
    Optional<Account> account = accountRepository.findById(id);
    return account.map(this::convertToDTO);
  }

  /**
   * Crea un nuovo account nel database con i dettagli forniti nel DTO di
   * registrazione dell'account. Se l'email è già in uso, viene lanciata
   * un'eccezione. Se l'account non può essere creato, viene lanciata
   * un'eccezione. Se l'account viene creato con successo, viene restituito
   * l'account creato.
   *
   * @param accountRegistrationDTO The account registration data containing the
   *                               account details.
   * @return The created account.
   * @throws RuntimeException If the email is already in use or if there is an
   *                          error creating the account.
   */
  @Override
  public AccountResponseDTO createAccount(AccountRegistrationDTO accountRegistrationDTO) {
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
      return convertToDTO(account);
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

  @Override
  public AccountResponseDTO convertToDTO(Account account) {
    AccountResponseDTO dto = new AccountResponseDTO();
    dto.setId(account.getId());
    dto.setName(account.getName());
    dto.setSurname(account.getSurname());
    dto.setEmail(account.getEmail());
    dto.setRoles(account.getRoles().stream()
        .map(role -> role.getName().toString()) // Converti RoleEnum in String
        .collect(Collectors.toSet()));
    // Supponiamo che tu abbia un metodo per convertire il byte array dell'immagine
    // in un URL:
    // dto.setProfilePictureUrl(convertToUrl(account.getProfilePicture()));
    return dto;
  }
}
