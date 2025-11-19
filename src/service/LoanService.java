package service;

import model.loan.*;
import model.user.Customer;
import repository.LoanRepository;
import repository.AccountRepository;
import exception.InvalidLoanException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import model.account.Account;

public class LoanService {
    private final LoanRepository loanRepository;
    private final AccountRepository accountRepository;

    public LoanService(LoanRepository loanRepository, AccountRepository accountRepository) {
        this.loanRepository = loanRepository;
        this.accountRepository = accountRepository;
    }

    public Loan createPersonalLoan(Customer customer, double amount, int tenureMonths) throws InvalidLoanException {
        try {
            String loanId = "LOAN-" + UUID.randomUUID();
            Loan loan = new PersonalLoan(loanId, customer, amount, tenureMonths);
            loanRepository.save(loan);
            return loan;
        } catch (IllegalArgumentException e) {
            throw new InvalidLoanException("Personal", e.getMessage());
        }
    }

    public Loan createHomeLoan(Customer customer, double amount, int tenureMonths, String propertyAddress, double propertyValue) throws InvalidLoanException {
        try {
            String loanId = "LOAN-" + UUID.randomUUID();
            Loan loan = new HomeLoan(loanId, customer, amount, tenureMonths, propertyAddress, propertyValue);
            loanRepository.save(loan);
            return loan;
        } catch (IllegalArgumentException e) {
            throw new InvalidLoanException("Home", e.getMessage());
        }
    }

    public Loan createAutoLoan(Customer customer, double amount, int tenureMonths, String vehicleModel, double vehicleValue, int registrationYear) throws InvalidLoanException {
        try {
            String loanId = "LOAN-" + UUID.randomUUID();
            Loan loan = new AutoLoan(loanId, customer, amount, tenureMonths, vehicleModel, vehicleValue, registrationYear);
            loanRepository.save(loan);
            return loan;
        } catch (IllegalArgumentException e) {
            throw new InvalidLoanException("Auto", e.getMessage());
        }
    }

    public Loan getLoan(String loanId) {
        return loanRepository.findById(loanId);
    }

    public List<Loan> getAllLoans() {
        return loanRepository.findAll();
    }

    public List<Loan> getLoansByCustomer(Customer customer) {
        return loanRepository.findByCustomer(customer);
    }

    public List<Loan> getLoansByStatus(LoanStatus status) {
        return loanRepository.findByStatus(status);
    }

    public List<Loan> getActiveLoans() {
        return loanRepository.findActiveLoan();
    }

    public List<Loan> getPendingLoans() {
        return loanRepository.findPendingLoans();
    }

    public List<Loan> getApprovedLoans() {
        return loanRepository.findApprovedLoans();
    }

    public void approveLoan(String loanId) throws Exception {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new Exception("Loan not found!");
        }

        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new Exception("Loan is not in PENDING status!");
        }

        loan.setStatus(LoanStatus.APPROVED);
        loanRepository.update(loan);
        System.out.println("[OK] Loan approved! ID: " + loanId);
    }

    public void activateLoan(String loanId, String disbursalAccountNumber) throws Exception {
        Loan loan = loanRepository.findById(loanId);
        Account account = accountRepository.findByAccountNumber(disbursalAccountNumber);

        if (loan == null) {
            throw new Exception("Loan not found!");
        }
        if (account == null) {
            throw new Exception("Account not found!");
        }

        if (!loan.getStatus().equals(LoanStatus.APPROVED)) {
            throw new Exception("Loan must be approved first!");
        }

        account.deposit(loan.getPrincipal());

        loan.setStatus(LoanStatus.ACTIVE);
        loan.setDisbursementDate(LocalDate.now());

        account.recordTransaction("LOAN DISBURSEMENT - " + loan.getPrincipal());

        loanRepository.update(loan);
        accountRepository.update(account);

        System.out.println("[OK] Loan activated! EUR " + loan.getPrincipal() + " disbursed to " + disbursalAccountNumber);
    }

    public void makePayment(String loanId, double paymentAmount) throws Exception {
        Loan loan = loanRepository.findById(loanId);

        if (loan == null) {
            throw new Exception("Loan not found!");
        }

        if (!loan.getStatus().equals(LoanStatus.ACTIVE)) {
            throw new Exception("Loan is not active! Current status: " + loan.getStatus());
        }

        double emi = loan.getEmiAmount();

        if (paymentAmount < emi) {
            throw new Exception("[LOW] EMI is EUR " + String.format("%.2f", emi) + ", but you paid EUR " + String.format("%.2f", paymentAmount));
        }

        double remainingBefore = loan.getRemainingBalance();
        double remainingAfter = remainingBefore - paymentAmount;

        if (remainingAfter <= 0) {
            loan.setStatus(LoanStatus.PAID_OFF);
            System.out.println("[OK] Loan paid off! Thank you for paying EUR " + String.format("%.2f", paymentAmount));
        } else {
            System.out.println("[INFO] Payment received! EUR " + String.format("%.2f", paymentAmount));
            System.out.println("[INFO] Remaining balance: EUR " + String.format("%.2f", remainingAfter));
        }

        LoanPayment payment = new LoanPayment(
                "PAY-" + UUID.randomUUID().toString(),
                loan,
                paymentAmount,
                java.time.YearMonth.now(),
                LocalDate.now()
        );

        loan.addPayment(payment);
        loanRepository.update(loan);

        System.out.println("[OK] Payment recorded!");
    }

    public void rejectLoan(String loanId, String reason) throws Exception {
        Loan loan = loanRepository.findById(loanId);
        if (loan == null) {
            throw new Exception("Loan not found!");
        }
        if (!loan.getStatus().equals(LoanStatus.PENDING)) {
            throw new Exception("Only pending loans can be rejected");
        }

        loan.rejectLoan(reason);
        loanRepository.update(loan);
        System.out.println("Loan " + loanId + " rejected. Reason: " + reason);
    }

    public double getTotalOutstandingBalance() {
        return loanRepository.findAll().stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .mapToDouble(Loan::getRemainingBalance)
                .sum();
    }

    public double getAverageLoanAmount() {
        return loanRepository.findAll().stream()
                .mapToDouble(Loan::getPrincipal)
                .average()
                .orElse(0.0);
    }

    public double getTotalDisbursedAmount() {
        return loanRepository.findAll().stream()
                .mapToDouble(Loan::getPrincipal)
                .sum();
    }

    public Loan createEducationLoan(Customer customer, double amount, int tenureMonths,
                                    String institutionName, String courseType, int courseDuration)
            throws InvalidLoanException {
        try {
            String loanId = "LOAN-" + UUID.randomUUID();
            Loan loan = new EducationLoan(loanId, customer, amount, tenureMonths, institutionName, courseType, courseDuration);
            loanRepository.save(loan);
            return loan;
        } catch (IllegalArgumentException e) {
            throw new InvalidLoanException("Education", e.getMessage());
        }
    }

    public Loan createBusinessLoan(Customer customer, double amount, int tenureMonths,
                                   String businessName, String businessType, double annualTurnover, double collateralValue)
            throws InvalidLoanException {
        try {
            String loanId = "LOAN-" + UUID.randomUUID();
            Loan loan = new BusinessLoan(loanId, customer, amount, tenureMonths, businessName, businessType, annualTurnover, collateralValue);
            loanRepository.save(loan);
            return loan;
        } catch (IllegalArgumentException e) {
            throw new InvalidLoanException("Business", e.getMessage());
        }
    }

}