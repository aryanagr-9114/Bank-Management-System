package com.aryan.demo.repository;

import com.aryan.demo.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    // Automatically generates: 
    // SELECT * FROM transactions WHERE from_account_id = ? OR to_account_id = ?
    List<Transaction> findByFromAccount_IdOrToAccount_Id(Long fromAccountId, Long toAccountId);
}
