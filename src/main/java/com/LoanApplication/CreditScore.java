package com.LoanApplication;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "credit_scores")
public class CreditScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Integer score;
    private String remarks; // e.g., Poor, Fair, Good, Excellent
    private LocalDateTime checkedDate;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Integer getScore() { return score; }
    public void setScore(Integer score) { this.score = score; }
    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }
    public LocalDateTime getCheckedDate() { return checkedDate; }
    public void setCheckedDate(LocalDateTime checkedDate) { this.checkedDate = checkedDate; }
}