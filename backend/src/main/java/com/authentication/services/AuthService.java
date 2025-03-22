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

import jakarta.security.auth.message.AuthException;

@Service
public class AuthService extends JwtUtils {

	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;


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

	
	public LoginResponseDTO register(RegistrationDTO dto) throws AuthException {
		String email = dto.getEmail();

		if (accountRepo.existsByEmail(email)) {
			throw new AuthException(AuthExceptionEnum.EMAIL_ALREADY_EXISTS.getCode());
		}

		Account account = new Account();
		account.setName(dto.getName());
		account.setSurname(dto.getSurname());
		account.setEmail(dto.getEmail());

		String encodedPassword = passwordEncoder.encode(dto.getPassword());
		account.setPassword(encodedPassword);

		Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
				.orElseThrow(() -> new RuntimeException("Error: Role not found."));

		account.getRoles().add(userRole);
		try {
			accountRepo.save(account);

			LoginRequestDTO loginDto = new LoginRequestDTO();
			loginDto.setEmail(account.getEmail());
			loginDto.setPassword(dto.getPassword());

			return login(loginDto);

		} catch (Exception e) {
			throw new RuntimeException("Error: Account not created.");
		}
	}
}
