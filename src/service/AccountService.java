package service;

import model.account.*;
import model.user.Customer;
import model.transaction.*;
import repository.*;
import exception.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class AccountService {

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public AccountService(AccountRepository accountRepository, TransactionRepository transactionRepository) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    public Account createSavingsAccount(Customer customer, double balance, double interestRate) throws InvalidAccountException {
        if (balance < 0) {
            throw new InvalidAccountException("Balance cannot be negative");
        }
        String accountNumber = "ACC" + System.currentTimeMillis();
        Account account = new SavingsAccount(accountNumber, customer, balance, interestRate);
        customer.addAccount(account);
        accountRepository.save(account);
        return account;
    }

    public Account createCurrentAccount(Customer customer, double balance, double overdraftLimit) throws InvalidAccountException {
        if (balance < 0) {
            throw new InvalidAccountException("Balance cannot be negative");
        }
        String accountNumber = "ACC" + System.currentTimeMillis();
        Account account = new CurrentAccount(accountNumber, customer, balance, overdraftLimit);
        customer.addAccount(account);
        accountRepository.save(account);
        return account;
    }

    public Account createFixedDepositAccount(Customer customer, double amount, double interestRate, int tenureMonths) throws InvalidAccountException {
        if (amount < 0) {
            throw new InvalidAccountException("Amount cannot be negative");
        }
        String accountNumber = "ACC" + System.currentTimeMillis();
        Account account = new FixedDepositAccount(accountNumber, customer, amount, interestRate, tenureMonths);
        customer.addAccount(account);
        accountRepository.save(account);
        return account;
    }

    public Account createRecurringDepositAccount(Customer customer, double initialBalance,
                                                 double monthlyDeposit, double interestRate,
                                                 int tenureMonths) throws InvalidAccountException {
        if (initialBalance < 0) {
            throw new InvalidAccountException("Initial balance cannot be negative");
        }
        String accountNumber = "ACC" + System.currentTimeMillis();
        Account account = new RecurringDepositAccount(accountNumber, customer, initialBalance,
                monthlyDeposit, interestRate, tenureMonths);
        customer.addAccount(account);
        accountRepository.save(account);
        return account;
    }

    public void deposit(String accountNumber, double amount) throws Exception {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new Exception("Account not found: " + accountNumber);
        }

        try {
            account.deposit(amount);

            String transactionId = "TXN-" + UUID.randomUUID();
            String transactionDesc = "DEPOSIT - " + amount + " - " + java.time.LocalDateTime.now();

            Transaction transaction = new Transaction(
                    transactionId,
                    account,
                    amount,
                    TransactionType.DEPOSIT,
                    java.time.LocalDateTime.now(),
                    "Deposit of " + amount
            );

            account.recordTransaction(transactionDesc);

            transactionRepository.save(transaction);
            accountRepository.update(account);

        } catch (Exception e) {
            throw new Exception("Deposit failed: " + e.getMessage());
        }
    }


    public void withdraw(String accountNumber, double amount) throws Exception {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new Exception("Account not found: " + accountNumber);
        }

        try {
            account.withdraw(amount);

            String transactionId = "TXN-" + UUID.randomUUID();
            String transactionDesc = "WITHDRAWAL - " + amount + " - " + java.time.LocalDateTime.now();

            Transaction transaction = new Transaction(
                    transactionId,
                    account,
                    amount,
                    TransactionType.WITHDRAWAL,
                    java.time.LocalDateTime.now(),
                    "Withdrawal of " + amount
            );

            account.recordTransaction(transactionDesc);

            transactionRepository.save(transaction);
            accountRepository.update(account);

        } catch (Exception e) {
            throw new Exception("Withdrawal failed: " + e.getMessage());
        }
    }


    public void transfer(String fromAccountNumber, String toAccountNumber, double amount) throws Exception {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber);
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber);

        if (fromAccount == null || toAccount == null) {
            throw new Exception("One or both accounts not found!");
        }

        try {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);

            String transactionId1 = "TXN-" + UUID.randomUUID();
            String transactionId2 = "TXN-" + UUID.randomUUID();

            String fromDesc = "TRANSFER OUT - " + amount + " to " + toAccountNumber;
            String toDesc = "TRANSFER IN - " + amount + " from " + fromAccountNumber;

            Transaction transaction1 = new Transaction(
                    transactionId1,
                    fromAccount,
                    amount,
                    TransactionType.TRANSFER,
                    java.time.LocalDateTime.now(),
                    "Transfer to account " + toAccountNumber
            );

            Transaction transaction2 = new Transaction(
                    transactionId2,
                    toAccount,
                    amount,
                    TransactionType.TRANSFER,
                    java.time.LocalDateTime.now(),
                    "Transfer from account " + fromAccountNumber
            );

            fromAccount.recordTransaction(fromDesc);
            toAccount.recordTransaction(toDesc);

            transactionRepository.save(transaction1);
            transactionRepository.save(transaction2);

            accountRepository.update(fromAccount);
            accountRepository.update(toAccount);

        } catch (Exception e) {
            throw new Exception("Transfer failed: " + e.getMessage());
        }
    }


    public void calculateMonthlyInterest() {
        List<Account> allAccounts = accountRepository.findAll();
        for (Account account : allAccounts) {
            account.calculateInterest();
            accountRepository.update(account);
        }
    }

    public String getAccountDetails(String accountNumber) throws Exception {
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if (account == null) {
            throw new Exception("Account not found!");
        }
        return account.getAccountDetails();
    }

    public void updateAccount(Account account) {
        accountRepository.update(account);
    }

    public List<Account> getActiveAccounts() {
        return accountRepository.findActiveAccounts();
    }
}