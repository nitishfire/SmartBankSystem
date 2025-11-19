package model.account;

import model.user.Customer;


public final class CurrentAccount extends Account {

    private double overdraftLimit;

    public CurrentAccount(String accountNumber, Customer customer, double balance, double overdraftLimit) {
        super(accountNumber, customer, balance);

        this.overdraftLimit = overdraftLimit;
        this.accountType = "Current";
    }

    @Override
    public void withdraw(double amount) throws Exception {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }

        if (amount > (this.balance + this.overdraftLimit)) {
            throw new Exception("Overdraft limit exceeded! Available: " + (this.balance + this.overdraftLimit));
        }
        this.balance -= amount;
    }

    @Override
    public void calculateInterest() {
        // Current accounts typically don't earn interest
    }

    @Override
    public String getAccountDetails() {
        return super.toString() + " | Overdraft Limit: Euro " + String.format("%.2f", this.overdraftLimit);
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}