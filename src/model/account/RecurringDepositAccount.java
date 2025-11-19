package model.account;

import model.user.Customer;


public final class RecurringDepositAccount extends Account {

    private double monthlyDeposit;
    private double interestRate;
    private int tenureMonths;
    private int monthsCompleted;

    public RecurringDepositAccount(String accountNumber, Customer customer, double initialBalance,
                                   double monthlyDeposit, double interestRate, int tenureMonths) {

        super(accountNumber, customer, initialBalance);

        this.monthlyDeposit = monthlyDeposit;
        this.interestRate = interestRate;
        this.tenureMonths = tenureMonths;
        this.monthsCompleted = 0;
        this.accountType = "Recurring Deposit";
    }

    @Override
    public void calculateInterest() {
        if (monthsCompleted < tenureMonths) {
            this.balance += monthlyDeposit;

            double interest = this.balance * this.interestRate / 12;
            this.balance += interest;

            monthsCompleted++;
        }
    }

    @Override
    public void withdraw(double amount) throws Exception {
        throw new Exception("Cannot withdraw from Recurring Deposit Account before maturity!");
    }

    @Override
    public String getAccountDetails() {
        return super.toString() + " | Monthly Deposit: Euro " + String.format("%.2f", monthlyDeposit) +
                " | Interest Rate: " + (interestRate * 100) + "% | Tenure: " + tenureMonths +
                " months | Completed: " + monthsCompleted + " months";
    }

    public double getMonthlyDeposit() {
        return monthlyDeposit;
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