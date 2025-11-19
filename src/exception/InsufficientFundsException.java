package exception;

public class InsufficientFundsException extends Exception {
    
    private final String accountNumber;
    private final double requiredAmount;
    private final double currentBalance;

    public InsufficientFundsException(String message) {
        super(message);
        this.accountNumber = null;
        this.requiredAmount = 0;
        this.currentBalance = 0;
    }

    public InsufficientFundsException(String accountNumber, double requiredAmount, double currentBalance) {
        super(String.format(
            "Insufficient funds in account %s. Required: Euro %.2f, Available: Euro %.2f",
            accountNumber, requiredAmount, currentBalance
        ));
        this.accountNumber = accountNumber;
        this.requiredAmount = requiredAmount;
        this.currentBalance = currentBalance;
    }

    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
        this.accountNumber = null;
        this.requiredAmount = 0;
        this.currentBalance = 0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public double getRequiredAmount() {
        return requiredAmount;
    }
    
    public double getCurrentBalance() {
        return currentBalance;
    }
    
    public double getShortfall() {
        return requiredAmount - currentBalance;
    }

    public String getDetailedMessage() {
        return String.format(
            "Account: %s | Required: Euro %.2f | Available: Euro %.2f | Shortfall: Euro %.2f",
            accountNumber, requiredAmount, currentBalance, getShortfall()
        );
    }
}
