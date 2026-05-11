package com.LoanApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;
public interface EMIRepository extends JpaRepository<EMI, Long> {

    @Query(value = "SELECT * FROM emi_details WHERE loan_application_id = :loanId LIMIT 1", nativeQuery = true)
    Optional<EMI> findByLoanIdNative(@Param("loanId") Long loanId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO emi_details (loan_application_id, principal_amount, interest_rate, tenure, monthly_emi, total_amount, total_interest, calculated_at) " +
                   "VALUES (:loanId, :principal, :rate, :tenure, :emi, :total, :interest, NOW())", nativeQuery = true)
    void saveEMINative(
        @Param("loanId") Long loanId,
        @Param("principal") Double principal,
        @Param("rate") Double rate,
        @Param("tenure") Integer tenure,
        @Param("emi") Double emi,
        @Param("total") Double total,
        @Param("interest") Double interest
    );
    @Query(value = "SELECT * FROM emis WHERE loan_app_id = :loanId LIMIT 1", nativeQuery = true)
    Optional<EMI> findByLoanAppIdNative(@Param("loanId") Long loanId);
}