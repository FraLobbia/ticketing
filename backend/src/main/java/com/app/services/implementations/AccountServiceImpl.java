package com.app.services.implementations;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.services.AccountService;
import com.authentication.models.dto.AccountDTO;
import com.authentication.models.dto.RegistrationDTO;
import com.authentication.models.entities.Account;
import com.authentication.models.entities.Role;
import com.authentication.models.enums.RoleEnum;
import com.authentication.repositories.AccountRepository;
import com.authentication.repositories.RoleRepository;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountRepository accountRepository;


//  /**
//   * Costruttore
//   */
//  public AccountServiceImpl(AccountRepository accountRepository, RoleRepository roleRepository,
//      PasswordEncoder passwordEncoder) {
//    this.accountRepository = accountRepository;
//    this.roleRepository = roleRepository;
//    this.passwordEncoder = passwordEncoder;
//  }

	/**
	 * Ottiene tutti gli account dal database.
	 *
	 * @return una lista di oggetti Account {@link Account}.
	 */
	@Override
	public List<AccountDTO> getAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		return accounts.stream().map(this::convertToDTO).collect(Collectors.toList());
	}

	/**
	 * Ottiene un account dal database in base all'ID fornito e lo mappa in un
	 * oggetto AccountResponseDTO.
	 *
	 * @param id L'ID dell'account da ottenere.
	 * @return un oggetto AccountResponseDTO {@link AccountDTO} contenente
	 *         l'account richiesto o vuoto se non esiste.
	 */
	@Override
	public Optional<AccountDTO> getAccountById(Long id) {
		Optional<Account> account = accountRepository.findById(id);
		return account.map(this::convertToDTO);
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
	public AccountDTO convertToDTO(Account account) {
		AccountDTO dto = new AccountDTO();
		dto.setId(account.getId());
		dto.setName(account.getName());
		dto.setSurname(account.getSurname());
		dto.setEmail(account.getEmail());
		dto.setRoles(account.getRoles().stream().map(role -> role.getName().toString()) // Converti RoleEnum in String
				.collect(Collectors.toSet()));
		// Supponiamo che tu abbia un metodo per convertire il byte array dell'immagine
		// in un URL:
		// dto.setProfilePictureUrl(convertToUrl(account.getProfilePicture()));
		return dto;
	}
}
