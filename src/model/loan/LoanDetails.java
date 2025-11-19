package model.loan;

import model.user.Customer;
import java.time.LocalDate;

public record LoanDetails(
    String loanId,
    Customer customer,
    double principal,
    double annualInterestRate,
    int tenureMonths,
    LocalDate startDate
) {

    public LoanDetails {
        if (loanId == null || loanId.isBlank()) {
            throw new IllegalArgumentException("Loan ID cannot be null or blank");
        }
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        if (principal <= 0) {
            throw new IllegalArgumentException("Principal must be positive");
        }
        if (annualInterestRate < 0) {
            throw new IllegalArgumentException("Interest rate cannot be negative");
        }
        if (tenureMonths <= 0) {
            throw new IllegalArgumentException("Tenure must be positive");
        }
    }

    public double calculateEMI() {
        double monthlyRate = annualInterestRate / 12 / 100;
        if (monthlyRate == 0) {
            return principal / tenureMonths;
        }
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) 
                / (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }
    
    public double getTotalPayable() {
        return calculateEMI() * tenureMonths;
    }
    
    public double getTotalInterest() {
        return getTotalPayable() - principal;
    }
    
    public LocalDate getMaturityDate() {
        return startDate.plusMonths(tenureMonths);
    }
    
    public String getFormattedPrincipal() {
        return String.format("Euro %.2f", principal);
    }
    
    public String getFormattedEMI() {
        return String.format("Euro %.2f", calculateEMI());
    }
    
    public String getFormattedTotalInterest() {
        return String.format("Euro %.2f", getTotalInterest());
    }
    
    public String getLoanDetails() {
        return String.format(
            "Loan ID: %s | Principal: %s | EMI: %s | Tenure: %d months | Total Interest: %s",
            loanId,
            getFormattedPrincipal(),
            getFormattedEMI(),
            tenureMonths,
            getFormattedTotalInterest()
        );
    }
}
