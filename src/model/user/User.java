package model.user;

import java.time.LocalDate;

public sealed abstract class User
        permits Customer, Teller, BankManager, Administrator {

    protected String userId;
    protected String name;
    protected String email;
    protected String password;
    protected LocalDate createdDate;

    public User(String userId, String name, String email, String password) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = hashPassword(password);
        this.createdDate = LocalDate.now();
    }

    protected static String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    public boolean verifyPassword(String inputPassword) {
        return this.password.equals(hashPassword(inputPassword));
    }

    public String getUserId() {return userId;}
    public String getName() {return name;}
    public String getEmail() {return email;}
    public String getPassword() {return password;}
    public LocalDate getCreatedDate() {return createdDate;}

    public abstract String getRole();

    @Override
    public String toString() {
        return userId + " - " + name + " (" + getRole() + ")";
    }
}
