package exception;

public class AccountNotFoundException extends Exception {

    private final String accountNumber;
    private final String searchCriteria;

    public AccountNotFoundException(String message) {
        super(message);
        this.accountNumber = null;
        this.searchCriteria = null;
    }

    public AccountNotFoundException(String accountNumber, String searchCriteria) {
        super(String.format(
                "Account not found with %s: %s",
                searchCriteria, accountNumber
        ));
        this.accountNumber = accountNumber;
        this.searchCriteria = searchCriteria;
    }

    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.accountNumber = null;
        this.searchCriteria = null;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public String getDetailedMessage() {
        return String.format(
                "Search Criteria: %s | Value: %s",
                searchCriteria != null ? searchCriteria : "Unknown",
                accountNumber != null ? accountNumber : "Unknown"
        );
    }
}