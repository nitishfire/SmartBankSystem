package exception;

public class InvalidAccountException extends RuntimeException {
    
    private final String accountNumber;
    private final String reason;

    public InvalidAccountException(String message) {
        super(message);
        this.accountNumber = null;
        this.reason = null;
    }

    public InvalidAccountException(String accountNumber, String reason) {
        super(String.format(
            "Invalid account: %s. Reason: %s",
            accountNumber, reason
        ));
        this.accountNumber = accountNumber;
        this.reason = reason;
    }

    public InvalidAccountException(String message, Throwable cause) {
        super(message, cause);
        this.accountNumber = null;
        this.reason = null;
    }

    public InvalidAccountException(String accountNumber, String reason, Throwable cause) {
        super(String.format(
            "Invalid account: %s. Reason: %s",
            accountNumber, reason
        ), cause);
        this.accountNumber = accountNumber;
        this.reason = reason;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getReason() {
        return reason;
    }

    public String getDetailedMessage() {
        return String.format(
            "Account: %s | Reason: %s",
            accountNumber != null ? accountNumber : "Unknown",
            reason != null ? reason : "Unknown"
        );
    }
}
