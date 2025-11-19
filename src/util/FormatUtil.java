package util;

public class FormatUtil {

    private FormatUtil() {
        throw new AssertionError("Cannot instantiate utility class");
    }

    public static String formatCurrency(double amount) {
        return "EUR " + String.format("%.2f", amount);
    }

    public static String formatCurrency(double amount, String currencySymbol) {
        return String.format("%s%.2f", currencySymbol, amount);
    }

    public static String formatPercentage(double value) {
        return String.format("%.2f%%", value);
    }

    public static String formatPercentage(double value, int decimalPlaces) {
        String format = "%." + decimalPlaces + "f%%";
        return String.format(format, value);
    }

    public static String formatNumber(double value) {
        return String.format("%,d", (long) value);
    }

    public static String formatDecimal(double value, int decimalPlaces) {
        String format = "%." + decimalPlaces + "f";
        return String.format(format, value);
    }

    public static String maskAccountNumber(String accountNumber) {
        if (accountNumber == null || accountNumber.length() < 4) {
            return "****";
        }
        int length = accountNumber.length();
        String lastFour = accountNumber.substring(length - 4);
        return "*".repeat(length - 4) + lastFour;
    }

    public static String formatPhoneNumber(String phone) {
        if (phone == null || phone.length() < 10) {
            return phone;
        }

        return "+" + phone.substring(0, 2) + " " + 
               phone.substring(2, 7) + " " + 
               phone.substring(7);
    }

    public static String formatTransactionId(String transactionId) {
        if (transactionId == null || transactionId.length() < 8) {
            return transactionId;
        }

        return transactionId.substring(0, 4) + "..." + 
               transactionId.substring(transactionId.length() - 4);
    }

    public static String formatBytes(long bytes) {
        if (bytes <= 0) return "0 B";
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(bytes) / Math.log10(1024));
        return String.format("%.1f %s", bytes / Math.pow(1024, digitGroups), units[digitGroups]);
    }

    public static String formatSeconds(long seconds) {
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        long secs = seconds % 60;
        
        if (hours > 0) {
            return String.format("%d:%02d:%02d", hours, minutes, secs);
        } else if (minutes > 0) {
            return String.format("%d:%02d", minutes, secs);
        } else {
            return String.format("%d seconds", secs);
        }
    }
    

    public static String capitalize(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        return value.substring(0, 1).toUpperCase() + value.substring(1).toLowerCase();
    }
    

    public static String capitalizeWords(String value) {
        if (value == null || value.isBlank()) {
            return value;
        }
        StringBuilder result = new StringBuilder();
        String[] words = value.split(" ");
        for (String word : words) {
            if (!word.isBlank()) {
                result.append(capitalize(word)).append(" ");
            }
        }
        return result.toString().trim();
    }
    

    public static String toTitleCase(String value) {
        return capitalizeWords(value);
    }

    public static String padLeft(String value, int length, char padChar) {
        if (value == null) {
            value = "";
        }
        if (value.length() >= length) {
            return value;
        }
        return String.valueOf(padChar).repeat(length - value.length()) + value;
    }

    public static String padRight(String value, int length, char padChar) {
        if (value == null) {
            value = "";
        }
        if (value.length() >= length) {
            return value;
        }
        return value + String.valueOf(padChar).repeat(length - value.length());
    }

    public static String truncate(String value, int maxLength) {
        if (value == null || value.length() <= maxLength) {
            return value;
        }
        return value.substring(0, maxLength - 3) + "...";
    }

    public static String removeWhitespace(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("\\s+", "");
    }

    public static String removeSpecialCharacters(String value) {
        if (value == null) {
            return null;
        }
        return value.replaceAll("[^a-zA-Z0-9]", "");
    }

    public static String getInitials(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            return "";
        }
        StringBuilder initials = new StringBuilder();
        String[] names = fullName.trim().split("\\s+");
        for (String name : names) {
            if (!name.isBlank()) {
                initials.append(name.charAt(0)).append(".");
            }
        }
        return initials.toString();
    }

    public static String formatBalanceStatus(double balance) {
        if (balance < 1000) {
            return "Low Balance: " + formatCurrency(balance);
        } else if (balance < 10000) {
            return "Balance: " + formatCurrency(balance);
        } else {
            return "Balance: " + formatCurrency(balance);
        }
    }

    public static String formatStatus(String status) {
        return switch (status.toUpperCase()) {
            case "ACTIVE" -> "✓ Active";
            case "INACTIVE" -> "✗ Inactive";
            case "PENDING" -> "⏳ Pending";
            case "APPROVED" -> "✓ Approved";
            case "REJECTED" -> "✗ Rejected";
            case "COMPLETED" -> "✓ Completed";
            case "FAILED" -> "✗ Failed";
            default -> status;
        };
    }

    public static String formatTableRow(String... columns) {
        StringBuilder row = new StringBuilder("|");
        for (String column : columns) {
            row.append(" ").append(padRight(column, 15, ' ')).append(" |");
        }
        return row.toString();
    }
    

    public static String createTableSeparator(int columnCount) {
        StringBuilder separator = new StringBuilder("+");
        for (int i = 0; i < columnCount; i++) {
            separator.append("-".repeat(17)).append("+");
        }
        return separator.toString();
    }

    public static String formatBoolean(boolean value) {
        return value ? "Yes" : "No";
    }
}
