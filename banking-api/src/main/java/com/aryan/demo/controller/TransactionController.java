package com.aryan.demo.controller;

import com.aryan.demo.dto.TransactionRequest;
import com.aryan.demo.dto.TransactionResponse;
import com.aryan.demo.dto.TransferRequest;
import com.aryan.demo.entity.Transaction;
import com.aryan.demo.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/deposit")
    public ResponseEntity<TransactionResponse> deposit(Authentication authentication, @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.deposit(authentication.getName(), request);
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @PostMapping("/withdraw")
    public ResponseEntity<TransactionResponse> withdraw(Authentication authentication, @RequestBody TransactionRequest request) {
        Transaction transaction = transactionService.withdraw(authentication.getName(), request);
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @PostMapping("/transfer")
    public ResponseEntity<TransactionResponse> transfer(Authentication authentication, @RequestBody TransferRequest request) {
        Transaction transaction = transactionService.transfer(authentication.getName(), request);
        return ResponseEntity.ok(new TransactionResponse(transaction));
    }

    @GetMapping("/account/{accountId}")
    public ResponseEntity<List<TransactionResponse>> getTransactionHistory(@PathVariable Long accountId, Authentication authentication) {
        List<TransactionResponse> history = transactionService.getTransactionHistory(accountId, authentication.getName())
                .stream()
                .map(TransactionResponse::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(history);
    }
}
