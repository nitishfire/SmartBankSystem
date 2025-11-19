package model.loan;

import model.user.Customer;

public class BusinessLoan extends Loan {
    private String businessName;
    private String businessType;
    private double annualTurnover;
    private double collateralValue;

    public BusinessLoan(String loanId, Customer customer, double principal, int tenureMonths, String businessName, String businessType, double annualTurnover, double collateralValue) {
        super(loanId, customer, principal, 0.08, tenureMonths);
        this.businessName = businessName;
        this.businessType = businessType;
        this.annualTurnover = annualTurnover;
        this.collateralValue = collateralValue;
    }

    @Override
    public void calculateEMI() {
        double monthlyRate = annualInterestRate / 12;
        emiAmount = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    @Override
    public String getLoanType() {
        return "Business Loan";
    }

    public String getBusinessLoanDetails() {
        return "Business Loan - ID: " + loanId + " | Amount: EUR " + principal +
                " | Business: " + businessName + " | Status: " + status;
    }

    public String getBusinessName() {
        return businessName;
    }

    public String getBusinessType() {
        return businessType;
    }

    public double getAnnualTurnover() {
        return annualTurnover;
    }

    public double getCollateralValue() {
        return collateralValue;
    }
}