package repository;

import model.account.Account;
import model.user.Customer;
import exception.AccountNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountRepository {
    private final Map<String, Account> accounts;
    
    public AccountRepository() {
        this.accounts = new HashMap<>();
    }

    public void save(Account account) {
        if (account == null) {
            throw new IllegalArgumentException("Account cannot be null");
        }
        accounts.put(account.getAccountNumber(), account);
        System.out.println("Account " + account.getAccountNumber() + " saved successfully.");
    }

    public Account findByAccountNumber(String accountNumber) throws AccountNotFoundException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new AccountNotFoundException(accountNumber);
        }
        return account;
    }

    public List<Account> findAll() {
        return new ArrayList<>(accounts.values());
    }

    public List<Account> findByCustomer(Customer customer) {
        return accounts.values().stream()
                .filter(a -> a.getCustomer().equals(customer))
                .toList();
    }

    public List<Account> findByAccountType(String accountType) {
        return accounts.values().stream()
                .filter(a -> a.getAccountType().equalsIgnoreCase(accountType))
                .toList();
    }

    public List<Account> findActiveAccounts() {
        return accounts.values().stream()
                .filter(Account::isActive)
                .toList();
    }

    public List<Account> findAccountsWithBalanceAbove(double threshold) {
        return accounts.values().stream()
                .filter(a -> a.getBalance() > threshold)
                .toList();
    }

    public List<Account> findAccountsWithBalanceBelow(double threshold) {
        return accounts.values().stream()
                .filter(a -> a.getBalance() < threshold)
                .toList();
    }

    public void update(Account account) {
        if (account != null && accounts.containsKey(account.getAccountNumber())) {
            accounts.put(account.getAccountNumber(), account);
            System.out.println("Account " + account.getAccountNumber() + " updated successfully.");
        }
    }

    public void delete(String accountNumber) {
        if (accounts.remove(accountNumber) != null) {
            System.out.println("Account " + accountNumber + " deleted successfully.");
        }
    }

    public boolean exists(String accountNumber) {
        return accounts.containsKey(accountNumber);
    }

    public int count() {
        return accounts.size();
    }

    public double getTotalBalance() {
        return accounts.values().stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public void deleteAll() {
        accounts.clear();
        System.out.println("All accounts deleted.");
    }
}
