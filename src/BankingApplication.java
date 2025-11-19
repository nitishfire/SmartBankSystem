import model.user.*;
import model.account.*;
import model.transaction.*;
import model.loan.*;
import repository.*;
import service.*;
import util.*;
import exception.*;
import java.util.Scanner;
import java.time.LocalDate;

public class BankingApplication {
    private static UserRepository userRepository;
    private static AccountRepository accountRepository;
    private static TransactionRepository transactionRepository;
    private static LoanRepository loanRepository;
    private static UserService userService;
    private static AccountService accountService;
    private static TransactionService transactionService;
    private static LoanService loanService;
    private static Scanner scanner;
    private static User loggedInUser;

    public static void main(String[] args) {
        initializeSystem();
        mainMenu();
    }

    private static void initializeSystem() {
        userRepository = new UserRepository();
        accountRepository = new AccountRepository();
        transactionRepository = new TransactionRepository();
        loanRepository = new LoanRepository();
        userService = new UserService(userRepository);
        accountService = new AccountService(accountRepository, transactionRepository);
        transactionService = new TransactionService(transactionRepository);
        loanService = new LoanService(loanRepository, accountRepository);
        scanner = new Scanner(System.in);
        System.out.println("SmartBank System initialized!");
    }

    private static void mainMenu() {
        while (true) {
            clearScreen();
            printBanner();
            if (loggedInUser == null) {
                loginMenu();
            } else if (loggedInUser instanceof Customer) {
                customerDashboard();
            } else {
                System.out.println("Access denied!");
                loggedInUser = null;
            }
        }
    }

    private static void printBanner() {
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║    SMARTBANK - REAL BANKING SYSTEM     ║");
        System.out.println("╚════════════════════════════════════════╝\n");
    }

    private static void loginMenu() {
        System.out.println("1. Login");
        System.out.println("2. Register New Account");
        System.out.println("3. Exit\n");
        System.out.print("Select option: ");
        int choice = getIntInput();
        switch (choice) {
            case 1 -> login();
            case 2 -> registerCustomer();
            case 3 -> {
                System.out.println("Thank you for using SmartBank!");
                System.exit(0);
            }
            default -> {
                System.out.println("Invalid option!");
                pressEnter();
            }
        }
    }

    private static void login() {
        clearScreen();
        printBanner();
        System.out.println("LOGIN");
        System.out.println("=".repeat(50));
        System.out.print("Enter Customer Name: ");
        String name = scanner.nextLine().trim();
        var customers = userService.getUsersByName(name);
        if (customers.isEmpty()) {
            System.out.println("Customer not found!");
            pressEnter();
            return;
        }
        Customer selectedCustomer = null;
        if (customers.size() == 1 && customers.get(0) instanceof Customer) {
            selectedCustomer = (Customer) customers.get(0);
        } else {
            System.out.println("\nMultiple customers found:");
            for (int i = 0; i < customers.size(); i++) {
                if (customers.get(i) instanceof Customer) {
                    System.out.println((i + 1) + ". " + customers.get(i).getName());
                }
            }
            System.out.print("Select customer: ");
            int selection = getIntInput() - 1;
            if (selection >= 0 && selection < customers.size() && customers.get(selection) instanceof Customer) {
                selectedCustomer = (Customer) customers.get(selection);
            }
        }
        if (selectedCustomer == null) {
            System.out.println("Invalid selection!");
            pressEnter();
            return;
        }
        System.out.print("Enter Password: ");
        String password = scanner.nextLine().trim();
        if (selectedCustomer.verifyPassword(password)) {
            loggedInUser = selectedCustomer;
            System.out.println("Login successful!");
            pressEnter();
        } else {
            System.out.println("Invalid password!");
            pressEnter();
        }
    }

    private static void registerCustomer() {
        clearScreen();
        printBanner();
        System.out.println("NEW ACCOUNT REGISTRATION");
        System.out.println("=".repeat(50));
        System.out.print("Enter Full Name: ");
        String name = scanner.nextLine().trim();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine().trim();
        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine().trim();
        System.out.print("Enter Address: ");
        String address = scanner.nextLine().trim();
        System.out.print("Enter Password (min 6 characters): ");
        String password = scanner.nextLine().trim();
        if (password.length() < 6) {
            System.out.println("Password must be at least 6 characters!");
            pressEnter();
            return;
        }
        System.out.print("Confirm Password: ");
        String confirmPassword = scanner.nextLine().trim();
        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords don't match!");
            pressEnter();
            return;
        }
        try {
            Customer newCustomer = new Customer("CUST-" + System.currentTimeMillis(), name, email, password, phone, address);
            userRepository.save(newCustomer);
            System.out.println("Registration successful!");
            System.out.println("Your ID: " + newCustomer.getUserId());
            pressEnter();
        } catch (Exception e) {
            System.out.println("Registration failed: " + e.getMessage());
            pressEnter();
        }
    }

    private static void customerDashboard() {
        Customer customer = (Customer) loggedInUser;
        clearScreen();
        printBanner();
        System.out.println("Welcome, " + customer.getName() + "!");
        System.out.println("=".repeat(50));
        System.out.println("1. Create New Account");
        System.out.println("2. View My Accounts");
        System.out.println("3. Deposit Money");
        System.out.println("4. Withdraw Money");
        System.out.println("5. Transfer Money");
        System.out.println("6. View Transaction History");
        System.out.println("7. Apply for Loan");
        System.out.println("8. View My Loans");
        System.out.println("9. Approve Loan");
        System.out.println("10. Activate Loan & Disburse");
        System.out.println("11. Repay Loan");
        System.out.println("12. Account Statement");
        System.out.println("13. My Profile");
        System.out.println("14. Logout\n");
        System.out.print("Select option: ");
        int choice = getIntInput();
        switch (choice) {
            case 1 -> createNewAccount(customer);
            case 2 -> viewMyAccounts(customer);
            case 3 -> depositMoney(customer);
            case 4 -> withdrawMoney(customer);
            case 5 -> transferMoney(customer);
            case 6 -> viewTransactionHistory(customer);
            case 7 -> applyForLoan(customer);
            case 8 -> viewMyLoans(customer);
            case 9 -> loanApproval(customer);
            case 10 -> activateLoanAndDisburse(customer);
            case 11 -> repayLoan(customer);
            case 12 -> accountStatement(customer);
            case 13 -> viewProfile(customer);
            case 14 -> {
                loggedInUser = null;
                System.out.println("Logged out successfully!");
                pressEnter();
                return;
            }
            default -> {
                System.out.println("Invalid option!");
                pressEnter();
            }
        }
    }

    private static void createNewAccount(Customer customer) {
        clearScreen();
        printBanner();
        System.out.println("CREATE NEW ACCOUNT");
        System.out.println("=".repeat(50));
        System.out.println("1. Savings Account (4% Interest)");
        System.out.println("2. Current Account (Overdraft)");
        System.out.println("3. Fixed Deposit Account (7% Interest)");
        System.out.println("4. Recurring Deposit Account (6% Interest)\n");
        System.out.print("Select type: ");
        int choice = getIntInput();
        try {
            switch (choice) {
                case 1 -> {
                    System.out.print("Initial deposit: EUR ");
                    double amount = getDoubleInput();
                    Account account = accountService.createSavingsAccount(customer, amount, 0.04);
                    System.out.println("[OK] Savings Account created!");
                    System.out.println("Account Number: " + account.getAccountNumber());
                }
                case 2 -> {
                    System.out.print("Initial deposit: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Overdraft limit: EUR ");
                    double overdraft = getDoubleInput();
                    Account account = accountService.createCurrentAccount(customer, amount, overdraft);
                    System.out.println("[OK] Current Account created!");
                    System.out.println("Account Number: " + account.getAccountNumber());
                }
                case 3 -> {
                    System.out.print("Deposit amount: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    Account account = accountService.createFixedDepositAccount(customer, amount, 0.07, tenure);
                    System.out.println("[OK] Fixed Deposit Account created!");
                    System.out.println("Account Number: " + account.getAccountNumber());
                }
                case 4 -> {
                    System.out.print("Initial balance: EUR ");
                    double initial = getDoubleInput();
                    System.out.print("Monthly deposit: EUR ");
                    double monthly = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    Account account = accountService.createRecurringDepositAccount(customer, initial, monthly, 0.06, tenure);
                    System.out.println("[OK] Recurring Deposit Account created!");
                    System.out.println("Account Number: " + account.getAccountNumber());
                }
                default -> System.out.println("Invalid option!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void viewMyAccounts(Customer customer) {
        clearScreen();
        printBanner();
        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            pressEnter();
            return;
        }
        System.out.println("YOUR ACCOUNTS");
        System.out.println("=".repeat(60));
        for (int i = 0; i < accounts.size(); i++) {
            Account acc = accounts.get(i);
            System.out.printf("%d. %s\n", i + 1, acc.getAccountType());
            System.out.printf("   Number: %s\n", acc.getAccountNumber());
            System.out.printf("   Balance: EUR %.2f\n", acc.getBalance());
            System.out.printf("   Status: %s\n\n", acc.isActive() ? "Active" : "Inactive");
        }
        pressEnter();
    }

    private static void depositMoney(Customer customer) {
        clearScreen();
        printBanner();
        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            pressEnter();
            return;
        }
        System.out.println("SELECT ACCOUNT:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s - EUR %.2f\n", i + 1, accounts.get(i).getAccountType(), accounts.get(i).getBalance());
        }
        System.out.print("Select: ");
        int accIdx = getIntInput() - 1;
        if (accIdx < 0 || accIdx >= accounts.size()) {
            System.out.println("Invalid account!");
            pressEnter();
            return;
        }
        System.out.print("Enter amount: EUR ");
        double amount = getDoubleInput();
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            pressEnter();
            return;
        }
        try {
            Account account = accounts.get(accIdx);
            double balanceBefore = account.getBalance();
            accountService.deposit(account.getAccountNumber(), amount);
            System.out.println("[OK] Deposit successful!");
            System.out.println("Amount: EUR " + String.format("%.2f", amount));
            System.out.println("Before: EUR " + String.format("%.2f", balanceBefore));
            System.out.println("After: EUR " + String.format("%.2f", account.getBalance()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void withdrawMoney(Customer customer) {
        clearScreen();
        printBanner();
        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            pressEnter();
            return;
        }
        System.out.println("SELECT ACCOUNT:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s - EUR %.2f\n", i + 1, accounts.get(i).getAccountType(), accounts.get(i).getBalance());
        }
        System.out.print("Select: ");
        int accIdx = getIntInput() - 1;
        if (accIdx < 0 || accIdx >= accounts.size()) {
            System.out.println("Invalid account!");
            pressEnter();
            return;
        }
        System.out.print("Enter amount: EUR ");
        double amount = getDoubleInput();
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            pressEnter();
            return;
        }
        try {
            Account account = accounts.get(accIdx);
            double balanceBefore = account.getBalance();
            accountService.withdraw(account.getAccountNumber(), amount);
            System.out.println("[OK] Withdrawal successful!");
            System.out.println("Amount: EUR " + String.format("%.2f", amount));
            System.out.println("Before: EUR " + String.format("%.2f", balanceBefore));
            System.out.println("After: EUR " + String.format("%.2f", account.getBalance()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void transferMoney(Customer customer) {
        clearScreen();
        printBanner();
        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            pressEnter();
            return;
        }
        System.out.println("FROM ACCOUNT:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s - EUR %.2f\n", i + 1, accounts.get(i).getAccountType(), accounts.get(i).getBalance());
        }
        System.out.print("From: ");
        int fromIdx = getIntInput() - 1;
        if (fromIdx < 0 || fromIdx >= accounts.size()) {
            System.out.println("Invalid account!");
            pressEnter();
            return;
        }
        System.out.print("To account number: ");
        String toAccNumber = scanner.nextLine().trim();
        System.out.print("Amount: EUR ");
        double amount = getDoubleInput();
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            pressEnter();
            return;
        }
        try {
            accountService.transfer(accounts.get(fromIdx).getAccountNumber(), toAccNumber, amount);
            System.out.println("[OK] Transfer successful!");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void viewTransactionHistory(Customer customer) {
        clearScreen();
        printBanner();
        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            pressEnter();
            return;
        }
        System.out.println("SELECT ACCOUNT:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, accounts.get(i).getAccountType());
        }
        System.out.print("Select: ");
        int accIdx = getIntInput() - 1;
        if (accIdx < 0 || accIdx >= accounts.size()) {
            System.out.println("Invalid account!");
            pressEnter();
            return;
        }
        var transactions = transactionService.getTransactionsByAccount(accounts.get(accIdx));
        if (transactions.isEmpty()) {
            System.out.println("No transactions found!");
            pressEnter();
            return;
        }
        System.out.println("TRANSACTION HISTORY");
        System.out.println("=".repeat(80));
        for (Transaction txn : transactions) {
            System.out.printf("%-12s | EUR %10.2f | %s\n", txn.type(), txn.amount(), txn.description());
        }
        pressEnter();
    }

    private static void applyForLoan(Customer customer) {
        clearScreen();
        printBanner();
        System.out.println("APPLY FOR LOAN");
        System.out.println("=".repeat(50));
        System.out.println("1. Personal Loan (9%)");
        System.out.println("2. Home Loan (6.5%)");
        System.out.println("3. Auto Loan (7.5%)");
        System.out.println("4. Education Loan (6.5%)");
        System.out.println("5. Business Loan (8%)\n");
        System.out.print("Select type: ");
        int loanType = getIntInput();
        try {
            switch (loanType) {
                case 1 -> {
                    System.out.print("Amount: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    Loan loan = loanService.createPersonalLoan(customer, amount, tenure);
                    System.out.println("[OK] Personal Loan created!");
                    System.out.println("ID: " + loan.getLoanId());
                    System.out.println("EMI: EUR " + String.format("%.2f", loan.getEmiAmount()));
                }
                case 2 -> {
                    System.out.print("Amount: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    System.out.print("Property address: ");
                    String address = scanner.nextLine().trim();
                    System.out.print("Property value: EUR ");
                    double propValue = getDoubleInput();
                    Loan loan = loanService.createHomeLoan(customer, amount, tenure, address, propValue);
                    System.out.println("[OK] Home Loan created!");
                    System.out.println("ID: " + loan.getLoanId());
                    System.out.println("EMI: EUR " + String.format("%.2f", loan.getEmiAmount()));
                }
                case 3 -> {
                    System.out.print("Amount: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    System.out.print("Vehicle model: ");
                    String vehicle = scanner.nextLine().trim();
                    System.out.print("Vehicle value: EUR ");
                    double vehicleValue = getDoubleInput();
                    System.out.print("Registration year: ");
                    int year = getIntInput();
                    Loan loan = loanService.createAutoLoan(customer, amount, tenure, vehicle, vehicleValue, year);
                    System.out.println("[OK] Auto Loan created!");
                    System.out.println("ID: " + loan.getLoanId());
                    System.out.println("EMI: EUR " + String.format("%.2f", loan.getEmiAmount()));
                }
                case 4 -> {
                    System.out.print("Amount: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    System.out.print("Institution name: ");
                    String institution = scanner.nextLine().trim();
                    System.out.print("Course type: ");
                    String course = scanner.nextLine().trim();
                    System.out.print("Course duration (months): ");
                    int duration = getIntInput();
                    Loan loan = loanService.createEducationLoan(customer, amount, tenure, institution, course, duration);
                    System.out.println("[OK] Education Loan created!");
                    System.out.println("ID: " + loan.getLoanId());
                    System.out.println("EMI: EUR " + String.format("%.2f", loan.getEmiAmount()));
                }
                case 5 -> {
                    System.out.print("Amount: EUR ");
                    double amount = getDoubleInput();
                    System.out.print("Tenure (months): ");
                    int tenure = getIntInput();
                    System.out.print("Business name: ");
                    String business = scanner.nextLine().trim();
                    System.out.print("Business type: ");
                    String type = scanner.nextLine().trim();
                    System.out.print("Annual turnover: EUR ");
                    double turnover = getDoubleInput();
                    System.out.print("Collateral value: EUR ");
                    double collateral = getDoubleInput();
                    Loan loan = loanService.createBusinessLoan(customer, amount, tenure, business, type, turnover, collateral);
                    System.out.println("[OK] Business Loan created!");
                    System.out.println("ID: " + loan.getLoanId());
                    System.out.println("EMI: EUR " + String.format("%.2f", loan.getEmiAmount()));
                }
                default -> System.out.println("Invalid option!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void viewMyLoans(Customer customer) {
        clearScreen();
        printBanner();
        var loans = loanService.getLoansByCustomer(customer);
        if (loans.isEmpty()) {
            System.out.println("No loans found!");
            pressEnter();
            return;
        }
        System.out.println("YOUR LOANS");
        System.out.println("=".repeat(80));
        for (Loan loan : loans) {
            System.out.printf("ID: %s | Type: %s\n", loan.getLoanId(), loan.getLoanType());
            System.out.printf("Amount: EUR %.2f | Status: %s\n", loan.getPrincipal(), loan.getStatus());
            System.out.printf("EMI: EUR %.2f | Remaining: EUR %.2f\n\n", loan.getEmiAmount(), loan.getRemainingBalance());
        }
        pressEnter();
    }

    private static void loanApproval(Customer customer) {
        clearScreen();
        printBanner();
        System.out.println("APPROVE LOAN");
        System.out.println("=".repeat(50));
        var loans = loanService.getLoansByCustomer(customer);
        if (loans.isEmpty()) {
            System.out.println("No loans found!");
            pressEnter();
            return;
        }
        System.out.println("YOUR PENDING LOANS:");
        int idx = 0;
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getStatus() == LoanStatus.PENDING) {
                idx++;
                System.out.printf("%d. %s - EUR %.2f\n", idx, loans.get(i).getLoanId(), loans.get(i).getPrincipal());
            }
        }
        System.out.print("Select loan to approve: ");
        int selection = getIntInput() - 1;
        try {
            loanService.approveLoan(loans.get(selection).getLoanId());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void activateLoanAndDisburse(Customer customer) {
        clearScreen();
        printBanner();
        System.out.println("ACTIVATE LOAN & DISBURSE");
        System.out.println("=".repeat(50));
        var loans = loanService.getLoansByCustomer(customer);
        var accounts = customer.getAccounts();
        if (loans.isEmpty() || accounts.isEmpty()) {
            System.out.println("No loans or accounts found!");
            pressEnter();
            return;
        }
        System.out.println("SELECT APPROVED LOAN:");
        int idx = 0;
        for (int i = 0; i < loans.size(); i++) {
            if (loans.get(i).getStatus() == LoanStatus.APPROVED) {
                idx++;
                System.out.printf("%d. %s - EUR %.2f\n", idx, loans.get(i).getLoanId(), loans.get(i).getPrincipal());
            }
        }
        System.out.print("Select loan: ");
        int loanIdx = getIntInput() - 1;
        System.out.println("SELECT ACCOUNT FOR DISBURSEMENT:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s\n", i + 1, accounts.get(i).getAccountNumber());
        }
        System.out.print("Select account: ");
        int accIdx = getIntInput() - 1;
        try {
            loanService.activateLoan(loans.get(loanIdx).getLoanId(), accounts.get(accIdx).getAccountNumber());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnter();
    }

    private static void repayLoan(Customer customer) {
        clearScreen();
        printBanner();
        System.out.println("REPAY LOAN");
        System.out.println("=".repeat(50));

        var loans = loanService.getLoansByCustomer(customer);
        var active = loans.stream().filter(l -> l.getStatus() == LoanStatus.ACTIVE).toList();

        if (active.isEmpty()) {
            System.out.println("No active loans!");
            pressEnter();
            return;
        }

        System.out.println("YOUR ACTIVE LOANS:");
        for (int i = 0; i < active.size(); i++) {
            System.out.printf("%d. %s - EMI: EUR %.2f\n", i + 1, active.get(i).getLoanId(), active.get(i).getEmiAmount());
        }

        System.out.print("Select loan: ");
        int loanIdx = getIntInput() - 1;
        Loan selectedLoan = active.get(loanIdx);

        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("You have no accounts to use for repayment!");
            pressEnter();
            return;
        }

        System.out.println("SELECT ACCOUNT TO PAY FROM:");
        for (int i = 0; i < accounts.size(); i++) {
            System.out.printf("%d. %s (%s) - EUR %.2f\n", i + 1, accounts.get(i).getAccountNumber(), accounts.get(i).getAccountType(), accounts.get(i).getBalance());
        }
        System.out.print("Select account: ");
        int accIdx = getIntInput() - 1;
        Account selectedAccount = accounts.get(accIdx);

        double emi = selectedLoan.getEmiAmount();
        System.out.printf("The EMI/payment is EUR %.2f. Proceed? (Y/N): ", emi);
        String proceed = scanner.nextLine().trim();
        if (!proceed.equalsIgnoreCase("Y")) {
            pressEnter();
            return;
        }

        if (selectedAccount.getBalance() < emi) {
            System.out.println("Error: Not enough balance in the selected account!");
            pressEnter();
            return;
        }

        try {
            selectedAccount.withdraw(emi);
            accountService.updateAccount(selectedAccount);
            loanService.makePayment(selectedLoan.getLoanId(), emi);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        pressEnter();
    }


    private static void accountStatement(Customer customer) {
        clearScreen();
        printBanner();
        var accounts = customer.getAccounts();
        if (accounts.isEmpty()) {
            System.out.println("No accounts found!");
            pressEnter();
            return;
        }
        System.out.println("ACCOUNT STATEMENT");
        System.out.println("=".repeat(60));
        for (Account acc : accounts) {
            System.out.printf("Account: %s (%s)\n", acc.getAccountNumber(), acc.getAccountType());
            System.out.printf("Opening Date: %s\n", acc.getOpeningDate());
            System.out.printf("Balance: EUR %.2f\n", acc.getBalance());
            System.out.printf("Status: %s\n", acc.isActive() ? "Active" : "Inactive");
            System.out.printf("Transactions: %d\n\n", acc.getTransactions().size());
        }
        pressEnter();
    }

    private static void viewProfile(Customer customer) {
        clearScreen();
        printBanner();
        System.out.println("MY PROFILE");
        System.out.println("=".repeat(50));
        System.out.printf("ID: %s\n", customer.getUserId());
        System.out.printf("Name: %s\n", customer.getName());
        System.out.printf("Email: %s\n", customer.getEmail());
        System.out.printf("Phone: %s\n", customer.getPhoneNumber() != null ? customer.getPhoneNumber() : "N/A");
        System.out.printf("Accounts: %d\n", customer.getAccountCount());
        pressEnter();
    }

    private static void clearScreen() {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }

    private static void pressEnter() {
        System.out.print("Press Enter to continue...");
        scanner.nextLine();
    }

    private static int getIntInput() {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    private static double getDoubleInput() {
        try {
            return Double.parseDouble(scanner.nextLine().trim());
        } catch (Exception e) {
            return -1;
        }
    }
}