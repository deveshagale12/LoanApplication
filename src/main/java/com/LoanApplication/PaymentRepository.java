package com.LoanApplication;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query(value = "SELECT * FROM payments WHERE loan_application_id = :loanId ORDER BY payment_date DESC", nativeQuery = true)
    List<Payment> findHistoryByLoanIdNative(@Param("loanId") Long loanId);
}