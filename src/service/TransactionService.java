package service;

import model.transaction.Transaction;
import model.transaction.TransactionType;
import model.account.Account;
import repository.TransactionRepository;
import exception.InvalidTransactionException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction recordTransaction(Account account, double amount, TransactionType type, String description)
            throws InvalidTransactionException {
        if (account == null) {
            throw new InvalidTransactionException("Transaction", "Account cannot be null");
        }
        if (amount <= 0) {
            throw new InvalidTransactionException("Transaction", "Amount must be positive", amount);
        }

        String transactionId = "TXN-" + UUID.randomUUID();
        Transaction transaction = new Transaction(
                transactionId,
                account,
                amount,
                type,
                java.time.LocalDateTime.now(),
                description
        );

        transactionRepository.save(transaction);
        return transaction;
    }

    public Transaction getTransaction(String transactionId) {
        return transactionRepository.findById(transactionId);
    }

    public List<Transaction> getTransactionsByAccount(Account account) {
        return transactionRepository.findByAccount(account);
    }

    public List<Transaction> getTransactionsByType(TransactionType type) {
        return transactionRepository.findByType(type);
    }

    public List<Transaction> getAllDeposits() {
        return transactionRepository.findDeposits();
    }

    public List<Transaction> getAllWithdrawals() {
        return transactionRepository.findWithdrawals();
    }

    public List<Transaction> getHighValueTransactions(double threshold) {
        return transactionRepository.findTransactionsAbove(threshold);
    }

    public List<Transaction> getTransactionsByDateRange(LocalDate startDate, LocalDate endDate) {
        return transactionRepository.findByDateRange(startDate, endDate);
    }

    public List<Transaction> getTransactionsByDate(LocalDate date) {
        return transactionRepository.findTransactionsByDate(date);
    }

    public TransactionStatistics getStatistics() {
        List<Transaction> allTransactions = transactionRepository.findAll();

        double totalAmount = allTransactions.stream()
                .mapToDouble(Transaction::amount)
                .sum();

        double averageAmount = allTransactions.stream()
                .mapToDouble(Transaction::amount)
                .average()
                .orElse(0.0);

        long totalCount = allTransactions.size();
        long depositCount = transactionRepository.findDeposits().size();
        long withdrawalCount = transactionRepository.findWithdrawals().size();
        long transferCount = transactionRepository.findTransfers().size();

        return new TransactionStatistics(totalAmount, averageAmount, totalCount, depositCount, withdrawalCount, transferCount);
    }

    public String getTransactionSummary(Transaction transaction) {
        return transaction.type() + ": Euro " + String.format("%.2f", transaction.amount());
    }

    public String getTransactionDetails(String transactionId) {
        Transaction transaction = transactionRepository.findById(transactionId);
        if (transaction == null) {
            return "Transaction not found";
        }

        return String.format(
                "Transaction ID: %s | Amount: Euro %.2f | Type: %s | Time: %s",
                transactionId,
                transaction.amount(),
                transaction.type(),
                transaction.timestamp()
        );
    }

    public double calculateTotalDeposits() {
        return transactionRepository.findDeposits().stream()
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public double calculateTotalWithdrawals() {
        return transactionRepository.findWithdrawals().stream()
                .mapToDouble(Transaction::amount)
                .sum();
    }

    public double getNetCashFlow() {
        double deposits = calculateTotalDeposits();
        double withdrawals = calculateTotalWithdrawals();
        return deposits - withdrawals;
    }

    public record TransactionStatistics(
            double totalAmount,
            double averageAmount,
            long totalCount,
            long depositCount,
            long withdrawalCount,
            long transferCount
    ) {
        public String getSummary() {
            return String.format(
                    "Total Amount: Euro %.2f | Average: Euro %.2f | Total Txns: %d | Deposits: %d | Withdrawals: %d | Transfers: %d",
                    totalAmount, averageAmount, totalCount, depositCount, withdrawalCount, transferCount
            );
        }
    }
}