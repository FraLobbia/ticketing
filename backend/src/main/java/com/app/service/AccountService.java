package com.app.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.models.entities.Account;
import com.authentication.models.entities.Role;
import com.authentication.models.enums.AuthExceptionEnum;
import com.authentication.models.enums.RoleEnum;
import com.authentication.repositories.AccountRepository;
import com.common.interfaces.BaseCrudService;

@Service
public class AccountService implements BaseCrudService<Account, Long> {

	@Autowired
	private AccountRepository repo;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Account create(Account e) {
		Role role = new Role();
		role.setName(RoleEnum.ROLE_USER);

		Set<Role> roles = new HashSet<>();
		roles.add(role);
		e.setRoles(roles);
		e.setCreatedAt(LocalDateTime.now());

		return repo.save(e);
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
}
