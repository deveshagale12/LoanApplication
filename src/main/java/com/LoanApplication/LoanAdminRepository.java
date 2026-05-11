package com.LoanApplication;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface LoanAdminRepository extends JpaRepository<Loan, Long> {
	@Query(value = "SELECT * FROM loans ORDER BY id DESC", nativeQuery = true)
    List<Loan> findAllApplicationsNative();

    @Transactional
    @Modifying
    @Query(value = "UPDATE loans SET status = :status WHERE id = :loanId", nativeQuery = true)
    void updateLoanStatusNative(@Param("loanId") Long loanId, @Param("status") String status);
}