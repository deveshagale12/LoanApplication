package com.LoanApplication;


import jakarta.persistence.*;

@Entity
@Table(name = "loan_types")
public class LoanType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String loanName;
    private Double maxAmount;
    private Double minAmount;
    private Double interestRate;
    private String description;
    private Double processingFee;

    public LoanType() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getLoanName() { return loanName; }
    public void setLoanName(String loanName) { this.loanName = loanName; }
    public Double getMaxAmount() { return maxAmount; }
    public void setMaxAmount(Double maxAmount) { this.maxAmount = maxAmount; }
    public Double getMinAmount() { return minAmount; }
    public void setMinAmount(Double minAmount) { this.minAmount = minAmount; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public Double getProcessingFee() { return processingFee; }
    public void setProcessingFee(Double processingFee) { this.processingFee = processingFee; }
}