package com.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authentication.models.dto.AccountDto;
import com.authentication.models.entities.Account;
import com.authentication.models.mapper.AccountMapper;
import com.authentication.services.AccountService;
import com.authentication.services.AuthService;
import com.common.abstracts.AbstractCrudController;
import com.common.interfaces.BaseCrudService;

@RestController
@RequestMapping("/accounts")
public class AccountController extends AbstractCrudController<AccountDto, Account, Long>  {

	@Autowired
	private AccountService service;
	@Autowired
	private AuthService authService;
	
	@Autowired
	private AccountMapper mapper;

	@Override
    protected BaseCrudService<Account, Long> getService() {
        return service;
    }

    @Override
    protected AccountDto toDto(Account e) {
        return mapper.toDto(e);
    }

    @Override
    protected Account toEntity(AccountDto dto) {
        return mapper.toEntity(dto);
    }
	/**
	 * Update account
	 * 
	 * @param id
	 * @param dto
	 * @return un oggetto {@link ResponseEntity} contenente l'account aggiornato o
	 *         un errore HTTP.
	 */
	@PutMapping("/{id}")
	@Override
	public ResponseEntity<AccountDto> update(@PathVariable Long id, @RequestBody AccountDto dto) {


		Account updatedAccount = service.update(toEntity(dto));

		if (updatedAccount == null) {
			return ResponseEntity.notFound().build();
		}

//		// Crea un oggetto LoginDTO per passare i dettagli dell'account all'AuthService
//		LoginRequestDTO loginDto = new LoginRequestDTO();
//		loginDto.setEmail(updatedAccount.get().getEmail());
//		loginDto.setPassword(updatedAccount.get().getPassword());
//
//		// Ottieni il token JWT aggiornato
//		LoginResponseDTO authResponseDto = authService.login(loginDto);
//
//		// Crea una risposta combinata con i dettagli dell'account aggiornato e il token
//		Map<String, Object> response = new HashMap<>();
//		response.put("account", updatedAccount);
//		response.put("auth", authResponseDto);
//
//		return ResponseEntity.ok(response);
		
		return null;
	}
}
