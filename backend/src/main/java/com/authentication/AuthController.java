package com.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.models.dto.LoginRequestDTO;
import com.authentication.models.dto.LoginResponseDTO;
import com.authentication.models.dto.RegistrationDTO;
import com.authentication.models.enums.AuthExceptionEnum;
import com.authentication.services.AuthService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginDto) {
		try {
			LoginResponseDTO responseDto = authService.login(loginDto);
			return ResponseEntity.ok(responseDto);
		} catch (UsernameNotFoundException | IllegalArgumentException e) {
			log.error("Errore durante il login: " + e.getMessage());

			AuthExceptionEnum exEnum = switch (e.getClass().getSimpleName()) {
			case "UsernameNotFoundException" -> AuthExceptionEnum.EMAIL_NOT_FOUND;
			case "IllegalArgumentException" -> AuthExceptionEnum.INVALID_PASSWORD;
			default -> AuthExceptionEnum.INVALID_CREDENTIALS;
			};
			
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new LoginResponseDTO(exEnum));
		} catch (Exception e) {
			log.error("Errore generico durante il login: " + e.getMessage());
			
			LoginResponseDTO responseDto = new LoginResponseDTO(AuthExceptionEnum.INVALID_CREDENTIALS);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseDto);
		}
	}

	@PostMapping("/register")
	public ResponseEntity<LoginResponseDTO> register(@RequestBody RegistrationDTO registrationDto) {
		LoginResponseDTO dtoResponse = authService.createAccount(registrationDto);
		if (dtoResponse == null) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
	}

}