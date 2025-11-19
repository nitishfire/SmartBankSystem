package model.user;

public final class BankManager extends User {

    private String branch;

    public BankManager(String userId, String name, String email, String password, String branch) {
        super(userId, name, email, password);
        this.branch = branch;
    }

    public String getBranch() {
        return branch;
    }

    @Override
    public String getRole() {
        return "Bank Manager";
    }
}
