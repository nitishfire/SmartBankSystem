package exception;

public class LoanApprovalException extends Exception {
    
    private final String loanId;
    private final String reason;
    private final double requestedAmount;

    public LoanApprovalException(String message) {
        super(message);
        this.loanId = null;
        this.reason = null;
        this.requestedAmount = 0;
    }

    public LoanApprovalException(String loanId, String reason) {
        super(String.format(
            "Loan approval failed for loan %s. Reason: %s",
            loanId, reason
        ));
        this.loanId = loanId;
        this.reason = reason;
        this.requestedAmount = 0;
    }

    public LoanApprovalException(String loanId, String reason, double requestedAmount) {
        super(String.format(
            "Loan approval failed for loan %s requesting Euro %.2f. Reason: %s",
            loanId, requestedAmount, reason
        ));
        this.loanId = loanId;
        this.reason = reason;
        this.requestedAmount = requestedAmount;
    }
    
    // Constructor with message and cause
    public LoanApprovalException(String message, Throwable cause) {
        super(message, cause);
        this.loanId = null;
        this.reason = null;
        this.requestedAmount = 0;
    }
    
    // Constructor with full details and cause
    public LoanApprovalException(String loanId, String reason, double requestedAmount, Throwable cause) {
        super(String.format(
            "Loan approval failed for loan %s requesting Euro %.2f. Reason: %s",
            loanId, requestedAmount, reason
        ), cause);
        this.loanId = loanId;
        this.reason = reason;
        this.requestedAmount = requestedAmount;
    }

    public String getLoanId() {
        return loanId;
    }
    
    public String getReason() {
        return reason;
    }
    
    public double getRequestedAmount() {
        return requestedAmount;
    }

    public String getDetailedMessage() {
        return String.format(
            "Loan ID: %s | Requested Amount: Euro %.2f | Reason: %s",
            loanId != null ? loanId : "Unknown",
            requestedAmount,
            reason != null ? reason : "Unknown"
        );
    }
}
