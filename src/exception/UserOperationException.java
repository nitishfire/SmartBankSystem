package exception;

public class UserOperationException extends Exception {
    
    private final String userId;
    private final String operation;
    private final String reason;

    public UserOperationException(String message) {
        super(message);
        this.userId = null;
        this.operation = null;
        this.reason = null;
    }

    public UserOperationException(String userId, String operation, String reason) {
        super(String.format(
            "User operation failed. User: %s | Operation: %s | Reason: %s",
            userId, operation, reason
        ));
        this.userId = userId;
        this.operation = operation;
        this.reason = reason;
    }

    public UserOperationException(String message, Throwable cause) {
        super(message, cause);
        this.userId = null;
        this.operation = null;
        this.reason = null;
    }

    public UserOperationException(String userId, String operation, String reason, Throwable cause) {
        super(String.format(
            "User operation failed. User: %s | Operation: %s | Reason: %s",
            userId, operation, reason
        ), cause);
        this.userId = userId;
        this.operation = operation;
        this.reason = reason;
    }

    public String getUserId() {
        return userId;
    }
    
    public String getOperation() {
        return operation;
    }
    
    public String getReason() {
        return reason;
    }

    public String getDetailedMessage() {
        return String.format(
            "User ID: %s | Operation: %s | Reason: %s",
            userId != null ? userId : "Unknown",
            operation != null ? operation : "Unknown",
            reason != null ? reason : "Unknown"
        );
    }
}
