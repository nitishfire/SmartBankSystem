package model.transaction;

import java.time.LocalDateTime;
import model.account.Account;

public record Transaction(
    String transactionId,
    Account account,
    double amount,
    TransactionType type,
    LocalDateTime timestamp,
    String description
) {
    public Transaction {
        if (transactionId == null || transactionId.isBlank()) {
            throw new IllegalArgumentException("Transaction ID cannot be null or blank");
        }
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
        if (type == null) {
            throw new IllegalArgumentException("Transaction type cannot be null");
        }
        if (timestamp == null) {
            throw new IllegalArgumentException("Timestamp cannot be null");
        }
    }

    public String getFormattedAmount() {
        return String.format("Euro %.2f", amount);
    }
    
    public String getTransactionDetails() {
        return String.format(
            "ID: %s | Type: %s | Amount: %s | Account: %s | Time: %s | Desc: %s",
            transactionId,
            type,
            getFormattedAmount(),
            account.getAccountNumber(),
            timestamp,
            description
        );
    }
    
    public boolean isWithdrawal() {
        return type == TransactionType.WITHDRAWAL;
    }
    
    public boolean isDeposit() {
        return type == TransactionType.DEPOSIT;
    }
    
    public boolean isTransfer() {
        return type == TransactionType.TRANSFER;
    }
}
