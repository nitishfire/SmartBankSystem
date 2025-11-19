package model.loan;

public enum LoanStatus {
    PENDING("Loan application is pending approval"),
    APPROVED("Loan has been approved by manager"),
    REJECTED("Loan application has been rejected"),
    ACTIVE("Loan is currently active and in repayment"),
    PAID_OFF("Loan has been fully paid off"),
    DEFAULT("Loan is in default - payments overdue"),
    CLOSED("Loan account has been closed");
    
    private final String description;
    
    LoanStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isActive() {
        return this == ACTIVE;
    }
    
    public boolean isTerminal() {
        return this == PAID_OFF || this == CLOSED || this == REJECTED;
    }
}
