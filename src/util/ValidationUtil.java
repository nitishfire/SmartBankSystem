package util;

public class ValidationUtil {

    private ValidationUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static boolean isValidString(String value) {
        return value != null && !value.isBlank();
    }

    public static boolean isValidAmount(double amount) {
        return amount > 0 && Double.isFinite(amount);
    }

    public static boolean isAmountInRange(double amount, double min, double max) {
        return amount >= min && amount <= max;
    }

    public static boolean isValidEmail(String email) {
        if (!isValidString(email)) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    }

    public static boolean isValidAccountNumber(String accountNumber) {
        if (!isValidString(accountNumber)) {
            return false;
        }
        return accountNumber.length() >= 8 && accountNumber.length() <= 20;
    }

    public static boolean isValidLoanId(String loanId) {
        if (!isValidString(loanId)) {
            return false;
        }
        return loanId.startsWith("LOAN-") && loanId.length() > 5;
    }

    public static boolean isValidCustomerId(String customerId) {
        if (!isValidString(customerId)) {
            return false;
        }
        return customerId.startsWith("CUST-") && customerId.length() > 5;
    }

    public static boolean isValidTransactionAmount(double amount) {
        final double MAX_TRANSACTION = 1000000.0;
        return isValidAmount(amount) && amount <= MAX_TRANSACTION;
    }

    public static boolean isValidBalance(double balance) {
        return balance >= 0 && Double.isFinite(balance);
    }

    public static boolean isValidInterestRate(double rate) {
        return rate >= 0 && rate <= 100;
    }

    public static boolean isValidTenure(int months) {
        return months >= 1 && months <= 360;
    }

    public static boolean isValidAge(int age) {
        return age >= 18 && age <= 100;
    }

    public static boolean isValidPhoneNumber(String phone) {
        if (!isValidString(phone)) {
            return false;
        }
        return phone.matches("^[+]?[0-9]{10,15}$");
    }

    public static boolean isValidIfscCode(String ifscCode) {
        if (!isValidString(ifscCode)) {
            return false;
        }
        return ifscCode.matches("^[A-Z]{4}0[A-Z0-9]{6}$");
    }

    public static boolean isValidBranchCode(String branchCode) {
        if (!isValidString(branchCode)) {
            return false;
        }
        return branchCode.length() >= 3 && branchCode.length() <= 10;
    }

    public static boolean isBetween(double value, double min, double max) {
        return value >= min && value <= max;
    }

    @FunctionalInterface
    public interface CustomValidator {
        boolean validate(String value);
    }

    public static boolean validateWith(String value, CustomValidator validator) {
        return validator.validate(value);
    }

    public static boolean areAllFieldsPresent(String... fields) {
        for (String field : fields) {
            if (!isValidString(field)) {
                return false;
            }
        }
        return true;
    }
}
