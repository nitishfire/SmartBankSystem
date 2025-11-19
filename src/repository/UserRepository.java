package repository;

import model.user.Customer;
import model.user.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepository {
    private final Map<String, User> users;
    
    public UserRepository() {
        this.users = new HashMap<>();
    }

    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        users.put(user.getUserId(), user);
        System.out.println("User " + user.getUserId() + " saved successfully.");
    }

    public User findById(String userId) {
        return users.get(userId);
    }

    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    public List<User> findByName(String name) {
        return users.values().stream()
                .filter(u -> u.getName().equalsIgnoreCase(name))
                .toList();
    }

    public List<User> findByEmail(String email) {
        return users.values().stream()
                .filter(u -> u.getEmail() != null && u.getEmail().equalsIgnoreCase(email))
                .toList();
    }

    public List<Customer> findAllCustomers() {
        return users.values().stream()
                .filter(u -> u instanceof Customer)
                .map(u -> (Customer) u)
                .toList();
    }

    public void update(User user) {
        if (user != null && users.containsKey(user.getUserId())) {
            users.put(user.getUserId(), user);
            System.out.println("User " + user.getUserId() + " updated successfully.");
        }
    }

    public void delete(String userId) {
        if (users.remove(userId) != null) {
            System.out.println("User " + userId + " deleted successfully.");
        }
    }

    public boolean exists(String userId) {
        return users.containsKey(userId);
    }

    public int count() {
        return users.size();
    }

    public void deleteAll() {
        users.clear();
        System.out.println("All users deleted.");
    }
}
