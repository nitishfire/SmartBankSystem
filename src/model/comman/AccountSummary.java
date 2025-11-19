package model.common;

import java.time.LocalDate;

public record AccountSummary(
    String accountNumber,
    String customerName,
    String accountType,
    double balance,
    LocalDate openingDate,
    int transactionCount
) {

    public AccountSummary {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }
        if (customerName == null || customerName.isBlank()) {
            throw new IllegalArgumentException("Customer name cannot be null or blank");
        }
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        if (transactionCount < 0) {
            throw new IllegalArgumentException("Transaction count cannot be negative");
        }
    }
    

    public String getFormattedBalance() {
        return String.format("Euro %.2f", balance);
    }
    
    public String getSummaryString() {
        return String.format(
            "Account: %s | Customer: %s | Type: %s | Balance: %s | Transactions: %d",
            accountNumber, customerName, accountType, getFormattedBalance(), transactionCount
        );
    }
    
    public boolean isHighBalance() {
        return balance > 50000;
    }
}
