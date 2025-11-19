package model.loan;

public enum LoanType {
    PERSONAL("Personal Loan", 0.09, 60),
    HOME("Home Loan", 0.065, 360),
    AUTO("Auto Loan", 0.075, 120),
    EDUCATION("Education Loan", 0.07, 180),
    BUSINESS("Business Loan", 0.10, 84);
    
    private final String displayName;
    private final double defaultInterestRate;
    private final int maxTenureMonths;
    
    LoanType(String displayName, double defaultInterestRate, int maxTenureMonths) {
        this.displayName = displayName;
        this.defaultInterestRate = defaultInterestRate;
        this.maxTenureMonths = maxTenureMonths;
    }
    
    public String getDisplayName() {
        return displayName;
    }
    
    public double getDefaultInterestRate() {
        return defaultInterestRate;
    }
    
    public int getMaxTenureMonths() {
        return maxTenureMonths;
    }
    
    public String getDetails() {
        return String.format(
            "%s - Rate: %.2f%% | Max Tenure: %d months",
            displayName,
            defaultInterestRate * 100,
            maxTenureMonths
        );
    }
}
