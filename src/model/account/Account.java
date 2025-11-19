package model.account;

import model.user.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    protected String accountNumber;
    protected Customer customer;
    protected double balance;
    protected LocalDate openingDate;
    protected boolean isActive;
    protected String accountType;
    protected List<String> transactions;

    public Account(String accountNumber, Customer customer, double balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        this.openingDate = LocalDate.now();
        this.isActive = true;
        this.transactions = new ArrayList<>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        this.balance += amount;
    }

    public void withdraw(double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount > this.balance) {
            throw new Exception("Insufficient funds! Current balance: " + this.balance);
        }
        this.balance -= amount;
    }

    public abstract void calculateInterest();

    public String getAccountDetails() {
        return String.format("Account: %s | Type: %s | Balance: Euro %.2f | Status: %s",
                accountNumber, accountType, balance, isActive ? "Active" : "Inactive");
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getBalance() {
        return balance;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getAccountType() {
        return accountType;
    }

    public List<String> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public void addTransaction(String transaction) {
        transactions.add(transaction);
    }

    @Override
    public String toString() {
        return String.format("Account: %s | Type: %s | Balance: Euro %.2f | Status: %s",
                accountNumber, accountType, balance, isActive ? "Active" : "Inactive");
    }

    public void recordTransaction(String transactionDescription) {
        this.transactions.add(transactionDescription);
    }
}