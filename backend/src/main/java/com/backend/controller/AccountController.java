package com.backend.controller;

import com.backend.model.Account;
import com.backend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

  @Autowired
  private AccountService accountService;

  @GetMapping
  public List<Account> getAllAccounts() {
    return accountService.getAllAccounts();
  }

  @GetMapping("/{id}")
  public ResponseEntity<Account> getAccountById(@PathVariable Long id) {
    return ResponseEntity.of(accountService.getAccountById(id));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Account> updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
    return ResponseEntity.of(accountService.updateAccount(id, accountDetails));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteAccount(@PathVariable Long id) {
    if (accountService.deleteAccount(id)) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.notFound().build();
    }
  }
}
