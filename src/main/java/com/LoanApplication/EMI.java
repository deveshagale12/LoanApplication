package com.LoanApplication;


import jakarta.persistence.*;
import java.time.LocalDateTime;


import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "emis")
public class EMI {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long loanAppId; 
    private Double principalAmount;
    private Double interestRate;
    private Integer tenureMonths;
    private Double monthlyEmi;
    private Double totalPayable;
    private LocalDateTime calculationDate;

    // Default Constructor
    public EMI() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getLoanAppId() { return loanAppId; }
    public void setLoanAppId(Long loanAppId) { this.loanAppId = loanAppId; }
    public Double getPrincipalAmount() { return principalAmount; }
    public void setPrincipalAmount(Double principalAmount) { this.principalAmount = principalAmount; }
    public Double getInterestRate() { return interestRate; }
    public void setInterestRate(Double interestRate) { this.interestRate = interestRate; }
    public Integer getTenureMonths() { return tenureMonths; }
    public void setTenureMonths(Integer tenureMonths) { this.tenureMonths = tenureMonths; }
    public Double getMonthlyEmi() { return monthlyEmi; }
    public void setMonthlyEmi(Double monthlyEmi) { this.monthlyEmi = monthlyEmi; }
    public Double getTotalPayable() { return totalPayable; }
    public void setTotalPayable(Double totalPayable) { this.totalPayable = totalPayable; }
    public LocalDateTime getCalculationDate() { return calculationDate; }
    public void setCalculationDate(LocalDateTime calculationDate) { this.calculationDate = calculationDate; }
}