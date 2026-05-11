package com.LoanApplication;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long> {

    @Query(value = "SELECT * FROM loan_types", nativeQuery = true)
    List<LoanType> findAllNative();

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO loan_types (loan_name, max_amount, min_amount, interest_rate, description, processing_fee) " +
                   "VALUES (:loanName, :maxAmount, :minAmount, :interestRate, :description, :processingFee)", nativeQuery = true)
    void saveLoanTypeNative(
    	    @Param("loanName") String loanName, 
    	    @Param("maxAmount") Double maxAmount, 
    	    @Param("minAmount") Double minAmount, 
    	    @Param("interestRate") Double interestRate, 
    	    @Param("description") String description, 
    	    @Param("processingFee") Double processingFee
    	);
    
    @Transactional
    @Modifying
    @Query(value = "UPDATE loan_types SET loan_name = :loanName, max_amount = :maxAmount, " +
                   "min_amount = :minAmount, interest_rate = :interestRate, " +
                   "description = :description, processing_fee = :processingFee WHERE id = :id", nativeQuery = true)
    void updateLoanTypeNative(
        @Param("id") Long id,
        @Param("loanName") String loanName, 
        @Param("maxAmount") Double maxAmount, 
        @Param("minAmount") Double minAmount, 
        @Param("interestRate") Double interestRate, 
        @Param("description") String description, 
        @Param("processingFee") Double processingFee
    );

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM loan_types WHERE id = :id", nativeQuery = true)
    void deleteLoanTypeNative(@Param("id") Long id);
}