package model.transaction;


public enum TransactionType {
    DEPOSIT("Deposit", "Money added to account"),
    WITHDRAWAL("Withdrawal", "Money removed from account"),
    TRANSFER("Transfer", "Money transferred to another account"),
    INTEREST_CREDIT("Interest Credit", "Interest credited to account"),
    FEE_DEBIT("Fee Debit", "Service charge deducted");
    
    private final String displayName;
    private final String description;
    
    TransactionType(String displayName, String description) {
        this.displayName = displayName;
        this.description = description;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public String getDescription() {
        return description;
    }

    public static boolean isValidType(String type) {
        try {
            TransactionType.valueOf(type);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
