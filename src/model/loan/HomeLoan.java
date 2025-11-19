package model.loan;

import model.user.Customer;

public class HomeLoan extends Loan {
    private String propertyAddress;
    private double propertyValue;

    public HomeLoan(String loanId, Customer customer, double principal, int tenureMonths, String propertyAddress, double propertyValue) {
        super(loanId, customer, principal, 0.065, tenureMonths);
        this.propertyAddress = propertyAddress;
        this.propertyValue = propertyValue;
    }

    @Override
    public void calculateEMI() {
        double monthlyRate = annualInterestRate / 12;
        emiAmount = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    @Override
    public String getLoanType() {
        return "Home Loan";
    }

    public String getHomeLoanDetails() {
        return "Home Loan - ID: " + loanId + " | Amount: EUR " + principal +
                " | Property: " + propertyAddress + " | Status: " + status;
    }

    public String getPropertyAddress() {
        return propertyAddress;
    }

    public double getPropertyValue() {
        return propertyValue;
    }
}