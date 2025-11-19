package model.user;

public final class Administrator extends User {
    public Administrator(String userId, String name, String email, String password) {
        super(userId, name, email, password);
    }


    @Override
    public String getRole() {
        return "Administrator";
    }
}
