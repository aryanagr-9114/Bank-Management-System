package com.aryan.demo.repository;

import com.aryan.demo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
    // Automatically generates: SELECT * FROM accounts WHERE account_number = ?
    Optional<Account> findByAccountNumber(String accountNumber);

    // Spring magically writes the SQL: SELECT * FROM accounts WHERE user_id = ?
    List<Account> findByUser(com.aryan.demo.entity.User user);
}
