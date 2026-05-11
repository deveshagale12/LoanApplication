
package com.LoanApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

import org.springframework.stereotype.Repository; 
@Repository
public interface CreditScoreRepository extends JpaRepository<CreditScore, Long> {

    @Query(value = "SELECT * FROM credit_scores WHERE user_id = :userId ORDER BY checked_date DESC LIMIT 1", nativeQuery = true)
    Optional<CreditScore> findLatestByUserIdNative(@Param("userId") Long userId);
}