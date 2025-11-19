package model.common;

import java.time.LocalDateTime;
import model.account.Account;

public record TransferRequest(
    String requestId,
    Account fromAccount,
    Account toAccount,
    double amount,
    String purpose,
    LocalDateTime requestTime,
    String status
) {

    public TransferRequest {
        if (requestId == null || requestId.isBlank()) {
            throw new IllegalArgumentException("Request ID cannot be null or blank");
        }
        if (fromAccount == null || toAccount == null) {
            throw new IllegalArgumentException("Accounts cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        if (purpose == null || purpose.isBlank()) {
            throw new IllegalArgumentException("Purpose cannot be null or blank");
        }
    }

    public String getFormattedAmount() {
        return String.format("Euro %.2f", amount);
    }
    
    public boolean isValid() {
        return fromAccount.getBalance() >= amount;
    }
    
    public String getTransferDetails() {
        return String.format(
            "Transfer ID: %s | From: %s | To: %s | Amount: %s | Purpose: %s | Status: %s",
            requestId,
            fromAccount.getAccountNumber(),
            toAccount.getAccountNumber(),
            getFormattedAmount(),
            purpose,
            status
        );
    }
}
