package model.loan;

import model.user.Customer;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Loan {
    protected String loanId;
    protected Customer customer;
    protected double principal;
    protected double annualInterestRate;
    protected int tenureMonths;
    protected LocalDate disbursementDate;
    protected LoanStatus status;
    protected double emiAmount;
    protected List<LoanPayment> payments;

    public Loan(String loanId, Customer customer, double principal, double annualInterestRate, int tenureMonths) {
        this.loanId = loanId;
        this.customer = customer;
        this.principal = principal;
        this.annualInterestRate = annualInterestRate;
        this.tenureMonths = tenureMonths;
        this.disbursementDate = LocalDate.now();
        this.status = LoanStatus.PENDING;
        this.payments = new ArrayList<>();
        calculateEMI();
    }

    public abstract void calculateEMI();
    public abstract String getLoanType();

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = LoanStatus.valueOf(status.toUpperCase());
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setDisbursementDate(LocalDate date) {
        this.disbursementDate = date;
    }

    public void addPayment(LoanPayment payment) {
        this.payments.add(payment);
    }

    public double getRemainingBalance() {
        double totalPaid = payments.stream().mapToDouble(p -> p.amount()).sum();
        return principal - totalPaid;
    }

    public void rejectLoan(String reason) {
        this.status = LoanStatus.REJECTED;
    }

    public void activateLoan() {
        this.status = LoanStatus.ACTIVE;
        this.disbursementDate = LocalDate.now();
    }

    public String getLoanId() {
        return loanId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public double getPrincipal() {
        return principal;
    }

    public double getAnnualInterestRate() {
        return annualInterestRate;
    }

    public int getTenureMonths() {
        return tenureMonths;
    }

    public LocalDate getDisbursementDate() {
        return disbursementDate;
    }

    public double getEmiAmount() {
        return emiAmount;
    }

    public List<LoanPayment> getPayments() {
        return new ArrayList<>(payments);
    }
}