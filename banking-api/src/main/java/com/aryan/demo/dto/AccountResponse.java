package com.aryan.demo.dto;

import com.aryan.demo.entity.Account;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String ownerUsername;

    // A nice little tool to convert a raw Database Account into a safe Receipt
    public AccountResponse(Account account) {
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType();
        this.balance = account.getBalance();
        this.ownerUsername = account.getUser().getUsername(); // No password included!
    }
}
