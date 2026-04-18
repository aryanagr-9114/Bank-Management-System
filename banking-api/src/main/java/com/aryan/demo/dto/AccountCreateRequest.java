package com.aryan.demo.dto;

import lombok.Data;

@Data
public class AccountCreateRequest {
    // The internet just needs to tell us what kind of account they want!
    // E.g., "SAVINGS" or "CURRENT"
    private String accountType;
}
