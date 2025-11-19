package model.user;

public final class Teller extends User {

    private String branchCode;

    public Teller(String userId, String name, String email, String password, String branchCode) {
        super(userId, name, email, password);
        this.branchCode = branchCode;
    }

    public String getBranchCode() {
        return branchCode;
    }

    @Override
    public String getRole() {
        return "Teller";
    }
}
