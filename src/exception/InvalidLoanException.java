package exception;

public class InvalidLoanException extends RuntimeException {
    
    private final String loanId;
    private final String reason;

    public InvalidLoanException(String message) {
        super(message);
        this.loanId = null;
        this.reason = null;
    }

    public InvalidLoanException(String loanId, String reason) {
        super(String.format(
            "Invalid loan: %s. Reason: %s",
            loanId, reason
        ));
        this.loanId = loanId;
        this.reason = reason;
    }

    public InvalidLoanException(String message, Throwable cause) {
        super(message, cause);
        this.loanId = null;
        this.reason = null;
    }

    public InvalidLoanException(String loanId, String reason, Throwable cause) {
        super(String.format(
            "Invalid loan: %s. Reason: %s",
            loanId, reason
        ), cause);
        this.loanId = loanId;
        this.reason = reason;
    }

    public String getLoanId() {
        return loanId;
    }
    
    public String getReason() {
        return reason;
    }

    public String getDetailedMessage() {
        return String.format(
            "Loan ID: %s | Reason: %s",
            loanId != null ? loanId : "Unknown",
            reason != null ? reason : "Unknown"
        );
    }
}
