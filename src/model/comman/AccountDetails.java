package model.common;

import java.time.LocalDate;

public final class AccountDetails {
    private final String accountNumber;
    private final String ifscCode;
    private final String branchName;
    private final String branchCity;
    private final LocalDate openingDate;

    public AccountDetails(String accountNumber, String ifscCode, String branchName,
                         String branchCity, LocalDate openingDate) {
        if (accountNumber == null || accountNumber.isBlank()) {
            throw new IllegalArgumentException("Account number cannot be null or blank");
        }
        if (ifscCode == null || ifscCode.isBlank()) {
            throw new IllegalArgumentException("IFSC code cannot be null or blank");
        }
        if (branchName == null || branchName.isBlank()) {
            throw new IllegalArgumentException("Branch name cannot be null or blank");
        }
        if (openingDate == null) {
            throw new IllegalArgumentException("Opening date cannot be null");
        }
        
        this.accountNumber = accountNumber;
        this.ifscCode = ifscCode;
        this.branchName = branchName;
        this.branchCity = branchCity;
        this.openingDate = openingDate;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    
    public String getIfscCode() {
        return ifscCode;
    }
    
    public String getBranchName() {
        return branchName;
    }
    
    public String getBranchCity() {
        return branchCity;
    }
    
    public LocalDate getOpeningDate() {
        return openingDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AccountDetails that)) return false;
        return accountNumber.equals(that.accountNumber) &&
               ifscCode.equals(that.ifscCode) &&
               branchName.equals(that.branchName);
    }
    
    @Override
    public int hashCode() {
        int result = accountNumber.hashCode();
        result = 31 * result + ifscCode.hashCode();
        result = 31 * result + branchName.hashCode();
        return result;
    }
    
    @Override
    public String toString() {
        return String.format(
            "AccountDetails{accountNumber='%s', ifscCode='%s', branchName='%s', branchCity='%s', openingDate=%s}",
            accountNumber, ifscCode, branchName, branchCity, openingDate
        );
    }
}
