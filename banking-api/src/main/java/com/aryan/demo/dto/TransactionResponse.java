package com.aryan.demo.dto;

import com.aryan.demo.entity.Transaction;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionResponse {
    private Long id;
    private BigDecimal amount;
    private String type;
    private String description;
    private LocalDateTime createdAt;
    
    // Details
    private Long fromAccountId;
    private Long toAccountId;

    public TransactionResponse(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.type = transaction.getType();
        this.description = transaction.getDescription();
        this.createdAt = transaction.getCreatedAt();

        if (transaction.getFromAccount() != null) {
            this.fromAccountId = transaction.getFromAccount().getId();
        }
        if (transaction.getToAccount() != null) {
            this.toAccountId = transaction.getToAccount().getId();
        }
    }
}
