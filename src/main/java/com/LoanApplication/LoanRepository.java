
package com.LoanApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository; 
@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    @Query(value = "SELECT * FROM loans", nativeQuery = true)
    List<Loan> findAllLoansNative();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO loans (user_id, loan_type, loan_amount, interest_rate, tenure_months, monthly_income, employment_type, loan_purpose, status, applied_date) " +
                   "VALUES (:#{#l.userId}, :#{#l.loanType}, :#{#l.loanAmount}, :#{#l.interestRate}, :#{#l.tenureMonths}, :#{#l.monthlyIncome}, :#{#l.employmentType}, :#{#l.loanPurpose}, 'PENDING', NOW())", nativeQuery = true)
    void applyLoanNative(@Param("l") Loan loan);

    @Transactional
    @Modifying
    @Query(value = "UPDATE loans SET status = :#{#l.status}, approved_date = :#{#l.approvedDate}, rejected_reason = :#{#l.rejectedReason} WHERE id = :id", nativeQuery = true)
    void updateLoanStatusNative(@Param("id") Long id, @Param("l") Loan loan);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM loans WHERE id = :id", nativeQuery = true)
    void deleteLoanNative(@Param("id") Long id);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE loans SET status = :status, approved_date = :appDate, rejected_reason = :reason WHERE id = :id", nativeQuery = true)
    void updateLoanStatusNative(
        @Param("id") Long id, 
        @Param("status") String status, 
        @Param("appDate") LocalDateTime appDate, 
        @Param("reason") String reason
    );
}