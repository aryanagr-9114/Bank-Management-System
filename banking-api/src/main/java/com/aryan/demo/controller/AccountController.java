package com.aryan.demo.controller;

import com.aryan.demo.dto.AccountCreateRequest;
import com.aryan.demo.dto.AccountResponse;
import com.aryan.demo.entity.Account;
import com.aryan.demo.service.AccountService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;

    // The teller is told who the back-office worker is when they get hired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    // This handles: POST http://localhost:8080/api/accounts
    @PostMapping
    public ResponseEntity<?> createAccount(Authentication authentication, @RequestBody AccountCreateRequest paperForm) {

        // 1. Look at the wristband that the Scanner already verified for us
        String username = authentication.getName();

        // 2. Hand the piece of paper to the Back Office Worker to do the heavy lifting!
        Account rawAccount = accountService.createAccount(username, paperForm);
        return ResponseEntity.ok(new AccountResponse(rawAccount));
    }

    // This handles: GET http://localhost:8080/api/accounts
    @GetMapping
    public ResponseEntity<?> getMyAccounts(Authentication authentication) {
        String username = authentication.getName();
        
        // Go get the raw accounts, and convert ALL of them into secure receipts!
        java.util.List<AccountResponse> safeAccounts = accountService.getMyAccounts(username)
                .stream()
                .map(AccountResponse::new)
                .toList();

        return ResponseEntity.ok(safeAccounts);
    }

    // This handles: GET http://localhost:8080/api/accounts/1
    @GetMapping("/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id, Authentication authentication) {
        String username = authentication.getName();
        
        // This will automatically crash and get handled by the Customer Service Desk if it fails!
        Account rawAccount = accountService.getAccountById(id, username);
        
        return ResponseEntity.ok(new AccountResponse(rawAccount));
    }
}
