package model.transaction;

import java.time.LocalDateTime;

public sealed interface TransactionStatus 
    permits Pending, Completed, Failed, Reversed {
}

final class Pending implements TransactionStatus {
    private final String reason;
    private final LocalDateTime createdAt;
    
    public Pending(String reason) {
        this.reason = reason;
        this.createdAt = LocalDateTime.now();
    }
    
    public String getReason() {
        return reason;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    @Override
    public String toString() {
        return "Pending: " + reason;
    }
}

final class Completed implements TransactionStatus {
    private final LocalDateTime completedAt;
    private final String confirmationNumber;
    
    public Completed(LocalDateTime completedAt, String confirmationNumber) {
        this.completedAt = completedAt;
        this.confirmationNumber = confirmationNumber;
    }
    
    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    
    public String getConfirmationNumber() {
        return confirmationNumber;
    }
    
    @Override
    public String toString() {
        return "Completed at " + completedAt + " | Confirmation: " + confirmationNumber;
    }
}

final class Failed implements TransactionStatus {
    private final String errorMessage;
    private final String errorCode;
    private final LocalDateTime failedAt;
    
    public Failed(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
        this.failedAt = LocalDateTime.now();
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public String getErrorCode() {
        return errorCode;
    }
    
    public LocalDateTime getFailedAt() {
        return failedAt;
    }
    
    @Override
    public String toString() {
        return "Failed - " + errorCode + ": " + errorMessage;
    }
}

final class Reversed implements TransactionStatus {
    private final String refundId;
    private final LocalDateTime reversedAt;
    private final String reason;
    
    public Reversed(String refundId, String reason) {
        this.refundId = refundId;
        this.reversedAt = LocalDateTime.now();
        this.reason = reason;
    }
    
    public String getRefundId() {
        return refundId;
    }
    
    public LocalDateTime getReversedAt() {
        return reversedAt;
    }
    
    public String getReason() {
        return reason;
    }
    
    @Override
    public String toString() {
        return "Reversed - Refund ID: " + refundId + " | Reason: " + reason;
    }
}
