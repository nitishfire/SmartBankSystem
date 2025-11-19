package repository;

import model.loan.Loan;
import model.loan.LoanStatus;
import model.loan.LoanType;
import model.user.Customer;
import exception.LoanApprovalException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoanRepository {
    private final Map<String, Loan> loans;
    
    public LoanRepository() {
        this.loans = new HashMap<>();
    }

    public void save(Loan loan) {
        if (loan == null) {
            throw new IllegalArgumentException("Loan cannot be null");
        }
        loans.put(loan.getLoanId(), loan);
        System.out.println("Loan " + loan.getLoanId() + " saved successfully.");
    }

    public Loan findById(String loanId) {
        return loans.get(loanId);
    }

    public List<Loan> findAll() {
        return new ArrayList<>(loans.values());
    }

    public List<Loan> findByCustomer(Customer customer) {
        return loans.values().stream()
                .filter(l -> l.getCustomer().equals(customer))
                .toList();
    }

    public List<Loan> findByStatus(LoanStatus status) {
        return loans.values().stream()
                .filter(l -> l.getStatus() == status)
                .toList();
    }

    public List<Loan> findActiveLoan() {
        return loans.values().stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .toList();
    }

    public List<Loan> findPendingLoans() {
        return loans.values().stream()
                .filter(l -> l.getStatus() == LoanStatus.PENDING)
                .toList();
    }

    public List<Loan> findApprovedLoans() {
        return loans.values().stream()
                .filter(l -> l.getStatus() == LoanStatus.APPROVED)
                .toList();
    }

    public List<Loan> findLoansAbove(double threshold) {
        return loans.values().stream()
                .filter(l -> l.getPrincipal() > threshold)
                .toList();
    }

    public List<Loan> findLoansBelow(double threshold) {
        return loans.values().stream()
                .filter(l -> l.getPrincipal() < threshold)
                .toList();
    }

    public List<Loan> findLoansWithRemainingBalance() {
        return loans.values().stream()
                .filter(l -> l.getRemainingBalance() > 0)
                .toList();
    }

    public List<Loan> findDefaultedLoans() {
        return loans.values().stream()
                .filter(l -> l.getStatus() == LoanStatus.DEFAULT)
                .toList();
    }

    public double getTotalOutstandingPrincipal() {
        return loans.values().stream()
                .filter(l -> l.getStatus() == LoanStatus.ACTIVE)
                .mapToDouble(Loan::getRemainingBalance)
                .sum();
    }

    public double getAverageLoanAmount() {
        return loans.values().stream()
                .mapToDouble(Loan::getPrincipal)
                .average()
                .orElse(0.0);
    }

    public long countByStatus(LoanStatus status) {
        return loans.values().stream()
                .filter(l -> l.getStatus() == status)
                .count();
    }

    public void update(Loan loan) {
        if (loan != null && loans.containsKey(loan.getLoanId())) {
            loans.put(loan.getLoanId(), loan);
            System.out.println("Loan " + loan.getLoanId() + " updated successfully.");
        }
    }

    public void delete(String loanId) {
        if (loans.remove(loanId) != null) {
            System.out.println("Loan " + loanId + " deleted successfully.");
        }
    }

    public boolean exists(String loanId) {
        return loans.containsKey(loanId);
    }

    public int count() {
        return loans.size();
    }

    public void deleteAll() {
        loans.clear();
        System.out.println("All loans deleted.");
    }
}
