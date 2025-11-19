package model.loan;

import java.time.YearMonth;
import java.time.LocalDate;

public record LoanPayment(
    String paymentId,
    Loan loan,
    double amount,
    YearMonth paymentMonth,
    LocalDate paymentDate
) {
    public LoanPayment {
        if (paymentId == null || paymentId.isBlank()) {
            throw new IllegalArgumentException("Payment ID cannot be null or blank");
        }
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }
        if (amount <= 0) {
            throw new IllegalArgumentException("Payment amount must be positive");
        }
        if (paymentMonth == null) {
            throw new IllegalArgumentException("Payment month cannot be null");
        }
        if (paymentDate == null) {
            throw new IllegalArgumentException("Payment date cannot be null");
        }
    }

    public String getFormattedAmount() {
        return String.format("Euro %.2f", amount);
    }
    
    public String getPaymentDetails() {
        return String.format(
            "Payment ID: %s | Amount: %s | Month: %s | Date: %s | Loan: %s",
            paymentId,
            getFormattedAmount(),
            paymentMonth,
            paymentDate,
            loan.getLoanId()
        );
    }
    
    public boolean isOnTime() {
        return paymentDate.getDayOfMonth() <= 5;
    }
}
