package com.aryan.demo.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // The sender (can be null if it's a direct deposit into from an ATM)
    @ManyToOne
    @JoinColumn(name = "from_account_id")
    private Account fromAccount;

    // The receiver (can be null if it's an ATM withdrawal)
    @ManyToOne
    @JoinColumn(name = "to_account_id")
    private Account toAccount;

    @Column(nullable = false)
    private BigDecimal amount;

    // e.g., "DEPOSIT", "WITHDRAWAL", "TRANSFER"
    @Column(nullable = false)
    private String type;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    private String description;
}
