package com.LoanApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    @Query(value = "SELECT * FROM admins WHERE email = :email", nativeQuery = true)
    Optional<Admin> findByEmailNative(@Param("email") String email);
}