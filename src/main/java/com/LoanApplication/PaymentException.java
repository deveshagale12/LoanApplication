package com.LoanApplication;

/**
 * Custom exception for handling payment-specific business logic failures.
 * Extends RuntimeException to allow Spring's @ControllerAdvice to catch it.
 */
public class PaymentException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public PaymentException(String message) {
        super(message);
    }

    public PaymentException(String message, Throwable cause) {
        super(message, cause);
    }
}