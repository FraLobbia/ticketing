package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.service.interfaces.BaseCrudService;
import com.authentication.models.dto.AccountDTO;
import com.authentication.models.entities.Account;
import com.authentication.repositories.AccountRepository;

@Service
public class AccountService implements BaseCrudService<Account, Long> {

	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * Ottiene tutti gli account dal database.
	 *
	 * @return una lista di oggetti Account {@link Account}.
	 */
	public List<Account> readAll() {
		return accountRepository.findAll();
	}

	/**
	 * Ottiene un account dal database in base all'ID fornito e lo mappa in un
	 * oggetto AccountResponseDTO.
	 *
	 * @param id L'ID dell'account da ottenere.
	 * @return un oggetto AccountResponseDTO {@link AccountDTO} contenente
	 *         l'account richiesto o vuoto se non esiste.
	 */
	public Optional<Account> read(Long id) {
		return accountRepository.findById(id);
	}

	

	public Optional<Account> update(Long id, Account accountDetails) {
		Account account = accountRepository.findById(id).orElse(null);
		if (account == null) {
			return Optional.empty();
		}
		account.setName(accountDetails.getName());
		account.setSurname(accountDetails.getSurname());
		account.setEmail(accountDetails.getEmail());
		// account.setPassword(accountDetails.getPassword());w

		accountRepository.save(account);

		return Optional.of(account);

	}

	public boolean deleteAccount(Long id) {
		return accountRepository.findById(id).map(account -> {
			accountRepository.delete(account);
			return true;
		}).orElse(false);
	}

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

	@Override
	public Account create(Account entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Account update(Account entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
}
