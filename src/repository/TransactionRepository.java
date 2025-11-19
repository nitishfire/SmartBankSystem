package repository;

import model.transaction.Transaction;
import model.transaction.TransactionType;
import model.account.Account;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TransactionRepository {
    private final Map<String, Transaction> transactions;
    
    public TransactionRepository() {
        this.transactions = new HashMap<>();
    }

    public void save(Transaction transaction) {
        if (transaction == null) {
            throw new IllegalArgumentException("Transaction cannot be null");
        }
        transactions.put(transaction.transactionId(), transaction);
        System.out.println("Transaction " + transaction.transactionId() + " saved successfully.");
    }

    public Transaction findById(String transactionId) {
        return transactions.get(transactionId);
    }

    public List<Transaction> findAll() {
        return new ArrayList<>(transactions.values());
    }

    public List<Transaction> findByAccount(Account account) {
        return transactions.values().stream()
                .filter(t -> t.account().equals(account))
                .toList();
    }

    public List<Transaction> findByType(TransactionType type) {
        return transactions.values().stream()
                .filter(t -> t.type() == type)
                .toList();
    }

    public List<Transaction> findDeposits() {
        return transactions.values().stream()
                .filter(Transaction::isDeposit)
                .toList();
    }

    public List<Transaction> findWithdrawals() {
        return transactions.values().stream()
                .filter(Transaction::isWithdrawal)
                .toList();
    }

    public List<Transaction> findTransfers() {
        return transactions.values().stream()
                .filter(Transaction::isTransfer)
                .toList();
    }

    public List<Transaction> findTransactionsAbove(double threshold) {
        return transactions.values().stream()
                .filter(t -> t.amount() > threshold)
                .toList();
    }

    public List<Transaction> findTransactionsBelow(double threshold) {
        return transactions.values().stream()
                .filter(t -> t.amount() < threshold)
                .toList();
    }

    public List<Transaction> findByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactions.values().stream()
                .filter(t -> {
                    LocalDate txDate = t.timestamp().toLocalDate();
                    return !txDate.isBefore(startDate) && !txDate.isAfter(endDate);
                })
                .toList();
    }

    public List<Transaction> findTransactionsByDate(LocalDate date) {
        return transactions.values().stream()
                .filter(t -> t.timestamp().toLocalDate().equals(date))
                .toList();
    }

    public double getTotalTransactionAmount() {
        return transactions.values().stream()
                .mapToDouble(Transaction::amount) // Method reference
                .sum();
    }

    public double getAverageTransactionAmount() {
        return transactions.values().stream()
                .mapToDouble(Transaction::amount)
                .average()
                .orElse(0.0);
    }

    public void update(Transaction transaction) {
        if (transaction != null && transactions.containsKey(transaction.transactionId())) {
            transactions.put(transaction.transactionId(), transaction);
            System.out.println("Transaction " + transaction.transactionId() + " updated successfully.");
        }
    }

    public void delete(String transactionId) {
        if (transactions.remove(transactionId) != null) {
            System.out.println("Transaction " + transactionId + " deleted successfully.");
        }
    }

    public boolean exists(String transactionId) {
        return transactions.containsKey(transactionId);
    }

    public int count() {
        return transactions.size();
    }

    public void deleteAll() {
        transactions.clear();
        System.out.println("All transactions deleted.");
    }
}
