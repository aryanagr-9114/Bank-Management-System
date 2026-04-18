package com.aryan.demo.service;

import com.aryan.demo.dto.TransactionRequest;
import com.aryan.demo.dto.TransferRequest;
import com.aryan.demo.entity.Account;
import com.aryan.demo.entity.Transaction;
import com.aryan.demo.repository.AccountRepository;
import com.aryan.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final AccountRepository accountRepository;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, AccountRepository accountRepository) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.accountRepository = accountRepository;
    }

    @Transactional
    public Transaction deposit(String username, TransactionRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Deposit amount must be greater than zero");
        }

        Account account = accountService.getAccountById(request.getAccountId(), username);
        account.setBalance(account.getBalance().add(request.getAmount()));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setToAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setType("DEPOSIT");
        transaction.setDescription(request.getDescription());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction withdraw(String username, TransactionRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Withdrawal amount must be greater than zero");
        }

        Account account = accountService.getAccountById(request.getAccountId(), username);
        if (account.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds");
        }

        account.setBalance(account.getBalance().subtract(request.getAmount()));
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(account);
        transaction.setAmount(request.getAmount());
        transaction.setType("WITHDRAWAL");
        transaction.setDescription(request.getDescription());
        return transactionRepository.save(transaction);
    }

    @Transactional
    public Transaction transfer(String username, TransferRequest request) {
        if (request.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new RuntimeException("Transfer amount must be greater than zero");
        }

        Account fromAccount = accountService.getAccountById(request.getFromAccountId(), username);
        
        Account toAccount = accountRepository.findById(request.getToAccountId())
                .orElseThrow(() -> new RuntimeException("Target account not found!"));

        if (fromAccount.getId().equals(toAccount.getId())) {
            throw new RuntimeException("Cannot transfer to the same account");
        }

        if (fromAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds for transfer");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(request.getAmount()));
        toAccount.setBalance(toAccount.getBalance().add(request.getAmount()));
        
        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        Transaction transaction = new Transaction();
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setAmount(request.getAmount());
        transaction.setType("TRANSFER");
        transaction.setDescription(request.getDescription());
        
        return transactionRepository.save(transaction);
    }

    public List<Transaction> getTransactionHistory(Long accountId, String username) {
        Account account = accountService.getAccountById(accountId, username);
        return transactionRepository.findByFromAccount_IdOrToAccount_Id(account.getId(), account.getId());
    }
}
