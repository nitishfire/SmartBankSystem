package model.loan;

import model.user.Customer;

public class EducationLoan extends Loan {
    private String institutionName;
    private String courseType;
    private int courseDuration;

    public EducationLoan(String loanId, Customer customer, double principal, int tenureMonths, String institutionName, String courseType, int courseDuration) {
        super(loanId, customer, principal, 0.065, tenureMonths);
        this.institutionName = institutionName;
        this.courseType = courseType;
        this.courseDuration = courseDuration;
    }

    @Override
    public void calculateEMI() {
        double monthlyRate = annualInterestRate / 12;
        emiAmount = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    @Override
    public String getLoanType() {
        return "Education Loan";
    }

    public String getEducationLoanDetails() {
        return "Education Loan - ID: " + loanId + " | Amount: EUR " + principal +
                " | Institution: " + institutionName + " | Course: " + courseType + " | Status: " + status;
    }

    public String getInstitutionName() {
        return institutionName;
    }

    public String getCourseType() {
        return courseType;
    }

    public int getCourseDuration() {
        return courseDuration;
    }
}