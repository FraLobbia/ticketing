package com.authentication.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.authentication.jwt.JwtUtils;
import com.authentication.models.dto.LoginRequestDTO;
import com.authentication.models.dto.LoginResponseDTO;
import com.authentication.models.dto.RegistrationDTO;
import com.authentication.models.entities.Account;
import com.authentication.models.entities.CustomUserDetails;
import com.authentication.models.entities.Role;
import com.authentication.models.enums.AuthExceptionEnum;
import com.authentication.models.enums.RoleEnum;
import com.authentication.repositories.RoleRepository;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AuthService extends JwtUtils {

	@Autowired
	private AccountService service;
	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private RoleRepository roleRepository;

	public LoginResponseDTO login(LoginRequestDTO loginDto) {
		CustomUserDetails user = service.verifyAccount(loginDto.getEmail(), loginDto.getPassword());
		
		String jwtToken = generateJwtToken(user);
		setSecurityContext(user);
		
		return new LoginResponseDTO(jwtToken);
	}



	public LoginResponseDTO register(RegistrationDTO dto) throws AuthException {
		String email = dto.getEmail();

		if (service.existsByEmail(email)) {
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
			Account created = service.save(account);

			LoginRequestDTO loginDto = new LoginRequestDTO();
			loginDto.setEmail(created.getEmail());
			loginDto.setPassword(dto.getPassword());

			return login(loginDto);

		} catch (Exception e) {
			log.error("Errore durante la registrazione: {}", e.getMessage());
			throw new RuntimeException("Error: Account not created.");
		}
	}
	
	/**
	 * Estrae l'email dal token JWT e crea un oggetto di autenticazione con l'email
	 */
	public void setSecurityContext(CustomUserDetails user) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, null,
				user.getAuthorities());

		SecurityContextHolder.getContext().setAuthentication(authenticationToken);

	}



	/**
	 * Estrae il token JWT dall'header Authorization
	 * @param request TODO
	 */
	public String getTokenFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
	
		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7, bearerToken.length());
		}
		return null;
	}
}
