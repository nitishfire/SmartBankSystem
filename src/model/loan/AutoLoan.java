package model.loan;

import model.user.Customer;

public class AutoLoan extends Loan {
    private String vehicleModel;
    private double vehicleValue;
    private int registrationYear;

    public AutoLoan(String loanId, Customer customer, double principal, int tenureMonths, String vehicleModel, double vehicleValue, int registrationYear) {
        super(loanId, customer, principal, 0.075, tenureMonths);
        this.vehicleModel = vehicleModel;
        this.vehicleValue = vehicleValue;
        this.registrationYear = registrationYear;
    }

    @Override
    public void calculateEMI() {
        double monthlyRate = annualInterestRate / 12;
        emiAmount = (principal * monthlyRate * Math.pow(1 + monthlyRate, tenureMonths)) /
                (Math.pow(1 + monthlyRate, tenureMonths) - 1);
    }

    @Override
    public String getLoanType() {
        return "Auto Loan";
    }

    public String getAutoLoanDetails() {
        return "Auto Loan - ID: " + loanId + " | Amount: EUR " + principal +
                " | Vehicle: " + vehicleModel + " | Status: " + status;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public double getVehicleValue() {
        return vehicleValue;
    }

    public int getRegistrationYear() {
        return registrationYear;
    }
}