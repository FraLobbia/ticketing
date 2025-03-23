package com.authentication.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.models.entities.Account;
import com.authentication.models.entities.Role;
import com.authentication.models.enums.AuthExceptionEnum;
import com.authentication.models.enums.RoleEnum;
import com.authentication.repositories.AccountRepository;
import com.authentication.repositories.RoleRepository;
import com.common.interfaces.BaseCrudService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AccountService implements BaseCrudService<Account, Long> {

	@Autowired
	private AccountRepository repo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepo;

	@Override
	public Account save(Account e) {
		Role userRole = roleRepo.findByName(RoleEnum.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role not found."));

		e.getRoles().add(userRole);
		e.setCreatedAt(LocalDateTime.now());

		try {
			Account saved = repo.save(e);
			return saved;
		} catch (Exception ex) {
			log.error("[save] Errore durante il salvataggio dell'account", ex);
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Account> readAll() {
		return repo.findAll();
	}

	@Override
	public Optional<Account> read(Long id) {
		Account e = repo.findById(id)
				.orElseThrow(() -> new RuntimeException("[read] Account con id (" + id + ") non trovato nel db"));
		return Optional.of(e);
	}

	@Override
	public Account update(Account entity) {
		Account a = repo.findById(entity.getId()).orElseThrow(
				() -> new RuntimeException("[update] Account con id (" + entity.getId() + ") non trovato nel db"));

		a.setName(entity.getName());
		a.setSurname(entity.getSurname());
		a.setEmail(entity.getEmail());
		a.setPassword(entity.getPassword());
		a.setRoles(entity.getRoles());
		a.setProfilePicture(entity.getProfilePicture());
		a.setUpdatedAt(LocalDateTime.now());

		return repo.save(a);
	}

	@Override
	public void delete(Long id) {
		try {
			repo.deleteById(id);
		} catch (Exception e) {
			throw new RuntimeException("[delete] Errore durante la cancellazione dell'account con id (" + id + ")");
		}
	}

	/**
	 * Verifica le credenziali dell'account nel database e restituisce l'account
	 * autenticato. Se l'account non è presente nel database, viene lanciata
	 * un'eccezione.
	 */
	public Account verifyAccount(String email, String password) throws UsernameNotFoundException {
		Account account = repo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException(AuthExceptionEnum.EMAIL_NOT_FOUND.getMessage()));

		if (!passwordEncoder.matches(password, account.getPassword())) {
			throw new IllegalArgumentException(AuthExceptionEnum.INVALID_PASSWORD.getMessage());
		}

		return account;
	}

	public boolean existsByEmail(String email) {
		return repo.existsByEmail(email);
	}
}
