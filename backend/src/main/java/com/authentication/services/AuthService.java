package com.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.authentication.jwt.JwtUtils;
import com.authentication.models.dto.LoginRequestDTO;
import com.authentication.models.dto.LoginResponseDTO;
import com.authentication.models.dto.RegistrationDTO;
import com.authentication.models.entities.Account;
import com.authentication.models.entities.Role;
import com.authentication.models.enums.AuthExceptionEnum;
import com.authentication.models.enums.RoleEnum;
import com.authentication.repositories.AccountRepository;
import com.authentication.repositories.RoleRepository;

@Service
public class AuthService extends JwtUtils {

	@Autowired
	private AccountRepository accountRepository;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	/**
	 * Imposta il contesto di sicurezza con l'utente autenticato e genera un token
	 * JWT da restituire al client.
	 */
	public LoginResponseDTO login(LoginRequestDTO loginDto) {
		String jwtToken = generateJwtToken(loginDto.getEmail(), loginDto.getPassword());

		setSecurityContext(jwtToken);
		return new LoginResponseDTO(jwtToken);
	}

	/**
	 * Estrae l'email dal token JWT e crea un oggetto di autenticazione con l'email
	 */
	public void setSecurityContext(String token) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				extractEmailFromToken(token), null, null);
		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

	}

	/**
	 * Verifica le credenziali dell'account nel database e restituisce l'account
	 * autenticato. Se l'account non è presente nel database, viene lanciata
	 * un'eccezione.
	 */
    public Account verifyAccount(String email, String password) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(AuthExceptionEnum.EMAIL_NOT_FOUND.getMessage()));
        
        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw new IllegalArgumentException(AuthExceptionEnum.INVALID_PASSWORD.getMessage());
        }
        
        return account;
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
	public LoginResponseDTO createAccount(RegistrationDTO accountRegistrationDTO) {
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

			LoginRequestDTO loginDto = new LoginRequestDTO();
			loginDto.setEmail(account.getEmail());
			loginDto.setPassword(accountRegistrationDTO.getPassword());

			return login(loginDto);

		} catch (Exception e) {
			throw new RuntimeException("Error: Account not created.");
		}
	}

	/**
	 * Carica un account dal database in base all'email fornita.
	 * 
	 * @param email L'email dell'account.
	 * @return Un oggetto UserDetails che rappresenta l'account.
	 * @throws UsernameNotFoundException Se l'account non è presente nel database.
	 */
	public Account loadUserByEmail(String email) throws UsernameNotFoundException {
		Account account = accountRepository.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("Account not found with email: " + email));
		return account;
	}
}
