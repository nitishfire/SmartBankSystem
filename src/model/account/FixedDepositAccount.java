package model.account;

import model.user.Customer;

public final class FixedDepositAccount extends Account {

    private double interestRate;
    private int tenureMonths;
    private int monthsCompleted;

    public FixedDepositAccount(String accountNumber, Customer customer, double amount,
                               double interestRate, int tenureMonths) {
        super(accountNumber, customer, amount);

        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.monthsCompleted = 0;
        this.accountType = "Fixed Deposit";
    }

    @Override
    public void withdraw(double amount) throws Exception {
        throw new Exception("Cannot withdraw from Fixed Deposit Account before maturity!");
    }

    @Override
    public void calculateInterest() {
        if (monthsCompleted < tenureMonths) {
            double interest = this.balance * this.interestRate / 12;
            this.balance += interest;
            monthsCompleted++;
        }
    }

    @Override
    public String getAccountDetails() {
        return super.toString() + " | Interest Rate: " + (this.interestRate * 100) +
                "% | Tenure: " + this.tenureMonths + " months | Completed: " + this.monthsCompleted + " months";
    }

    public double getInterestRate() {
        return interestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public int getMonthsCompleted() {
        return monthsCompleted;
    }
}