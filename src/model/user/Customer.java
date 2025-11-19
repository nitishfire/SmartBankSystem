package model.user;

import java.util.ArrayList;
import java.util.List;
import model.account.Account;

public final class Customer extends User {

    private List<Account> accounts;
    private String phoneNumber;
    private String address;

    public Customer(String userId, String name, String email, String password) {
        super(userId, name, email, password);
        this.accounts = new ArrayList<>();
    }

    public Customer(String userId, String name, String email, String password, String phoneNumber, String address) {
        super(userId, name, email, password);
        this.accounts = new ArrayList<>();
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public void addAccount(Account account) {
        if (!accounts.contains(account)) {
            accounts.add(account);
        }
    }

    public List<Account> getAccounts() {
        return new ArrayList<>(accounts);
    }

    public int getAccountCount() {
        return accounts.size();
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer customer)) return false;
        return userId.equals(customer.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }
}
