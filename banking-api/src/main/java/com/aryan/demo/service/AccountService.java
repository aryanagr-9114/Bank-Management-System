package com.aryan.demo.service;

import com.aryan.demo.dto.AccountCreateRequest;
import com.aryan.demo.entity.Account;
import com.aryan.demo.entity.User;
import com.aryan.demo.repository.AccountRepository;
import com.aryan.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

// The @Service label tells Spring Boot: "This guy is a Back Office worker"
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    // The worker is handed the keys to the Vaults when he is hired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account createAccount(String username, AccountCreateRequest paperForm) {
        
        // 1. Fetch the physical user from the Vault
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found!");
        }

        // 2. Generate a random 10 digit account number
        String newAccountNumber = String.valueOf((long) (Math.random() * 10000000000L));

        // 3. Create the physical Account Binder
        Account newAccount = new Account();
        newAccount.setUser(userOptional.get());
        newAccount.setAccountNumber(newAccountNumber);
        newAccount.setAccountType(paperForm.getAccountType());
        newAccount.setBalance(BigDecimal.ZERO); // Starts with $0.00

        // 4. Save it into the Vault!
        return accountRepository.save(newAccount);
    }

    public List<Account> getMyAccounts(String username) {
        // Find the user, then find all accounts linked to them!
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));
        
        return accountRepository.findByUser(user);
    }

    public Account getAccountById(Long id, String username) {
        // 1. Fetch physical folder from Vault (Thows exception if not found, caught by GlobalExceptionHandler!)
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found!"));
        
        // 2. We CANNOT let hackers view someone else's account. Make sure the username matches!
        if (!account.getUser().getUsername().equals(username)) {
            throw new RuntimeException("Unauthorized: You do not own this account!");
        }
        
        return account;
    }
}
