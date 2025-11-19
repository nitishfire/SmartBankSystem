package service;

import model.user.*;
import repository.UserRepository;
import exception.UserOperationException;
import exception.InvalidAccountException;
import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserRepository userRepository;
    
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Customer createCustomer(String name, String email, String password, String phone, String address) throws UserOperationException {
        if (name == null || name.isBlank()) {
            throw new UserOperationException("Customer", "create", "Name cannot be null or blank");
        }
        if (email == null || email.isBlank()) {
            throw new UserOperationException("Customer", "create", "Email cannot be null or blank");
        }
        if (password == null || password.isBlank()) {
            throw new UserOperationException("Customer", "create", "Password cannot be null or blank");
        }
        String userId = "CUST-" + java.util.UUID.randomUUID();
        Customer customer = new Customer(userId, name, email, password, phone, address);
        userRepository.save(customer);
        return customer;
    }

    public Teller createTeller(String name, String email, String branchCode, String password) throws UserOperationException {
        if (name == null || name.isBlank()) {
            throw new UserOperationException("Teller", "create", "Name cannot be null or blank");
        }
        if (branchCode == null || branchCode.isBlank()) {
            throw new UserOperationException("Teller", "create", "Branch code cannot be null or blank");
        }

        String userId = "TELLER-" + UUID.randomUUID();
        Teller teller = new Teller(userId, name, email, password, branchCode);
        userRepository.save(teller);
        System.out.println("Teller created: " + userId);
        return teller;
    }

    public BankManager createBankManager(String name, String email, String branch, String password) throws UserOperationException {
        if (name == null || name.isBlank()) {
            throw new UserOperationException("Manager", "create", "Name cannot be null or blank");
        }
        if (branch == null || branch.isBlank()) {
            throw new UserOperationException("Manager", "create", "Branch cannot be null or blank");
        }

        String userId = "MGR-" + UUID.randomUUID();
        BankManager manager = new BankManager(userId, name, email, password, branch);
        userRepository.save(manager);
        System.out.println("Bank Manager created: " + userId);
        return manager;
    }

    public Administrator createAdministrator(String name, String email, String password) throws UserOperationException {
        if (name == null || name.isBlank()) {
            throw new UserOperationException("Admin", "create", "Name cannot be null or blank");
        }
        
        String userId = "ADMIN-" + UUID.randomUUID();
        Administrator admin = new Administrator(userId, name, email, password);
        userRepository.save(admin);
        System.out.println("Administrator created: " + userId);
        return admin;
    }

    public User getUserById(String userId) {
        return userRepository.findById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByName(String name) {
        return userRepository.findByName(name);
    }

    public List<User> getUsersByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<Customer> getAllCustomers() {
        return userRepository.findAllCustomers();
    }

    public String getUserRole(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return "User not found";
        }

        return switch (user) {
            case Customer c -> "Customer: " + c.getRole() + " | Accounts: " + c.getAccounts().size();
            case Teller t -> "Teller: " + t.getRole() + " | Branch: " + t.getBranchCode();
            case BankManager m -> "Bank Manager: " + m.getRole() + " | Branch: " + m.getBranch();
            case Administrator a -> "Administrator: " + a.getRole() + " | Full System Access";
        };
    }

    public String getUserDashboard(String userId) {
        User user = userRepository.findById(userId);
        if (user == null) {
            return "User not found";
        }
        
        return switch (user) {
            case Customer c -> buildCustomerDashboard(c);
            case Teller t -> buildTellerDashboard(t);
            case BankManager m -> buildManagerDashboard(m);
            case Administrator a -> buildAdminDashboard(a);
        };
    }

    private String buildCustomerDashboard(Customer customer) {
        return String.format(
            "=== Customer Dashboard ===\n" +
            "ID: %s\nName: %s\nEmail: %s\nAccounts: %d\nRole: %s",
            customer.getUserId(),
            customer.getName(),
            customer.getEmail(),
            customer.getAccounts().size(),
            customer.getRole()
        );
    }

    private String buildTellerDashboard(Teller teller) {
        return String.format(
            "=== Teller Dashboard ===\n" +
            "ID: %s\nName: %s\nEmail: %s\nBranch: %s\nRole: %s",
            teller.getUserId(),
            teller.getName(),
            teller.getEmail(),
            teller.getBranchCode(),
            teller.getRole()
        );
    }

    private String buildManagerDashboard(BankManager manager) {
        return String.format(
            "=== Manager Dashboard ===\n" +
            "ID: %s\nName: %s\nEmail: %s\nBranch: %s\nRole: %s",
            manager.getUserId(),
            manager.getName(),
            manager.getEmail(),
            manager.getBranch(),
            manager.getRole()
        );
    }

    private String buildAdminDashboard(Administrator admin) {
        return String.format(
            "=== Administrator Dashboard ===\n" +
            "ID: %s\nName: %s\nEmail: %s\nRole: %s\nAccess Level: FULL",
            admin.getUserId(),
            admin.getName(),
            admin.getEmail(),
            admin.getRole()
        );
    }

    public UserStatistics getUserStatistics() {
        List<User> allUsers = userRepository.findAll();
        
        long totalUsers = allUsers.size();
        long customerCount = allUsers.stream().filter(u -> u instanceof Customer).count();
        long tellerCount = allUsers.stream().filter(u -> u instanceof Teller).count();
        long managerCount = allUsers.stream().filter(u -> u instanceof BankManager).count();
        long adminCount = allUsers.stream().filter(u -> u instanceof Administrator).count();
        
        return new UserStatistics(totalUsers, customerCount, tellerCount, managerCount, adminCount);
    }

    public void updateUserEmail(String userId, String newEmail) throws UserOperationException {
        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserOperationException(userId, "update", "User not found");
        }
        
        if (newEmail == null || newEmail.isBlank()) {
            throw new UserOperationException(userId, "update", "Email cannot be blank");
        }

        User updatedUser = switch (user) {
            case Customer c -> new Customer(c.getUserId(), c.getName(), newEmail, c.getPassword(), c.getPhoneNumber(), c.getAddress());
            case Teller t -> new Teller(t.getUserId(), t.getName(), newEmail, t.getPassword(), t.getBranchCode());
            case BankManager m -> new BankManager(m.getUserId(), m.getName(), newEmail, m.getPassword(), m.getBranch());
            case Administrator a -> new Administrator(a.getUserId(), a.getName(), newEmail, a.getPassword());
        };
        
        userRepository.update(updatedUser);
        System.out.println("User " + userId + " email updated.");
    }

    public void deleteUser(String userId) {
        userRepository.delete(userId);
        System.out.println("User " + userId + " deleted.");
    }
    

    public record UserStatistics(
        long totalUsers,
        long customerCount,
        long tellerCount,
        long managerCount,
        long adminCount
    ) {
        public String getSummary() {
            return String.format(
                "Total Users: %d | Customers: %d | Tellers: %d | Managers: %d | Admins: %d",
                totalUsers, customerCount, tellerCount, managerCount, adminCount
            );
        }
    }
}
