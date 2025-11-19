package model.common;

import java.time.LocalDate;

public record KYCInfo(
    String documentType,
    String documentNumber,
    String documentIssuingCountry,
    LocalDate issueDate,
    LocalDate expiryDate
) {
    public KYCInfo {
        if (documentType == null || documentType.isBlank()) {
            throw new IllegalArgumentException("Document type cannot be null or blank");
        }
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new IllegalArgumentException("Document number cannot be null or blank");
        }
        if (documentIssuingCountry == null || documentIssuingCountry.isBlank()) {
            throw new IllegalArgumentException("Document issuing country cannot be null or blank");
        }
        if (issueDate == null || expiryDate == null) {
            throw new IllegalArgumentException("Issue and expiry dates cannot be null");
        }
        if (issueDate.isAfter(expiryDate)) {
            throw new IllegalArgumentException("Issue date cannot be after expiry date");
        }
    }

    public boolean isValid() {
        return LocalDate.now().isBefore(expiryDate);
    }
    
    public boolean isExpiringSoon() {
        LocalDate thirtyDaysFromNow = LocalDate.now().plusDays(30);
        return LocalDate.now().isBefore(expiryDate) && expiryDate.isBefore(thirtyDaysFromNow);
    }
    
    public String getDocumentDetails() {
        return String.format(
            "Document Type: %s | Number: %s | Country: %s | Expiry: %s | Valid: %s",
            documentType,
            documentNumber,
            documentIssuingCountry,
            expiryDate,
            isValid() ? "Yes" : "No"
        );
    }
}
