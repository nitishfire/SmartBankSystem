package exception;

public class InvalidTransactionException extends RuntimeException {
    
    private final String transactionId;
    private final String reason;
    private final double amount;

    public InvalidTransactionException(String message) {
        super(message);
        this.transactionId = null;
        this.reason = null;
        this.amount = 0;
    }

    public InvalidTransactionException(String transactionId, String reason) {
        super(String.format(
            "Invalid transaction %s. Reason: %s",
            transactionId, reason
        ));
        this.transactionId = transactionId;
        this.reason = reason;
        this.amount = 0;
    }

    public InvalidTransactionException(String transactionId, String reason, double amount) {
        super(String.format(
            "Invalid transaction %s for amount Euro %.2f. Reason: %s",
            transactionId, amount, reason
        ));
        this.transactionId = transactionId;
        this.reason = reason;
        this.amount = amount;
    }
    
    // Constructor with message and cause
    public InvalidTransactionException(String message, Throwable cause) {
        super(message, cause);
        this.transactionId = null;
        this.reason = null;
        this.amount = 0;
    }
    
    // Constructor with full details and cause
    public InvalidTransactionException(String transactionId, String reason, double amount, Throwable cause) {
        super(String.format(
            "Invalid transaction %s for amount Euro %.2f. Reason: %s",
            transactionId, amount, reason
        ), cause);
        this.transactionId = transactionId;
        this.reason = reason;
        this.amount = amount;
    }

    public String getTransactionId() {
        return transactionId;
    }
    
    public String getReason() {
        return reason;
    }
    
    public double getAmount() {
        return amount;
    }

    public String getDetailedMessage() {
        return String.format(
            "Transaction ID: %s | Amount: Euro %.2f | Reason: %s",
            transactionId != null ? transactionId : "Unknown",
            amount,
            reason != null ? reason : "Unknown"
        );
    }
}
