package model.account;

import model.user.Customer;


public final class SavingsAccount extends Account {

    private double interestRate;

    public SavingsAccount(String accountNumber, Customer customer, double balance, double interestRate) {

        super(accountNumber, customer, balance);

        this.interestRate = interestRate;
        this.accountType = "Savings";
    }

    @Override
    public void calculateInterest() {

        double interest = this.balance * this.interestRate / 12;
        this.balance += interest;
    }


    @Override
    public String getAccountDetails() {
        return super.toString() + " | Interest Rate: " + (this.interestRate * 100) + "%";
    }

    public double getInterestRate() {
        return interestRate;
    }
}