package model.loan;

import model.user.Customer;

public class PersonalLoan extends Loan {
    public PersonalLoan(String loanId, Customer customer, double principal, int tenureMonths) {
        super(loanId, customer, principal, 0.09, tenureMonths);
    }

    @Override
    public void calculateEMI() {
        double monthlyRate = annualInterestRate / 12;
        emiAmount = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    @Override
    public String getLoanType() {
        return "Personal Loan";
    }

    public String getPersonalLoanDetails() {
        return "Personal Loan - ID: " + loanId + " | Amount: EUR " + principal +
                " | Status: " + status + " | EMI: EUR " + String.format("%.2f", emiAmount);
    }
}