package com.LoanApplication;


import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanApplicationId;
    private Double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod; // e.g., UPI, NetBanking, Card
    private String transactionId;
    private String paymentStatus; // SUCCESS, PENDING, FAILED

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLoanApplicationId() { return loanApplicationId; }
    public void setLoanApplicationId(Long loanApplicationId) { this.loanApplicationId = loanApplicationId; }
    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
}