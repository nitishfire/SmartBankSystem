package model.account;

import exception.InsufficientFundsException;

public interface Transactional {
    
    void deposit(double amount);
    
    void withdraw(double amount) throws InsufficientFundsException;
    
    double getBalance();

    default String getTransactionSummary() {
        return "Current Balance: Euro " + String.format("%.2f", getBalance());
    }

    static boolean isValidAmount(double amount) {
        return amount > 0 && amount <= 1000000;
    }

    private String formatCurrency(double amount) {
        return "Euro " + String.format("%.2f", amount);
    }

    default String getFormattedBalance() {
        return formatCurrency(getBalance());
    }
}
