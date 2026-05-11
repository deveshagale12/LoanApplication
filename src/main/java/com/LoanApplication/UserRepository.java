package com.LoanApplication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    Optional<User> findByEmail(@Param("email") String email);

    @Query(value = "SELECT * FROM users WHERE id = :id", nativeQuery = true)
    Optional<User> findByIdNative(@Param("id") Long id);

    @Query(value = "SELECT COUNT(*) FROM users WHERE email = :email", nativeQuery = true)
    int existsByEmailNative(@Param("email") String email);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO users (full_name, email, phone, password, aadhaar_number, pan_number, address, city, state, pincode, role, created_at, updated_at) " +
                   "VALUES (:#{#u.fullName}, :#{#u.email}, :#{#u.phone}, :#{#u.password}, :#{#u.aadhaarNumber}, :#{#u.panNumber}, :#{#u.address}, :#{#u.city}, :#{#u.state}, :#{#u.pincode}, :#{#u.role}, NOW(), NOW())", 
           nativeQuery = true)
    void registerUser(@Param("u") User user);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET full_name = :#{#u.fullName}, phone = :#{#u.phone}, address = :#{#u.address}, " +
                   "city = :#{#u.city}, state = :#{#u.state}, pincode = :#{#u.pincode}, updated_at = NOW() " +
                   "WHERE id = :id", nativeQuery = true)
    void updateUser(@Param("id") Long id, @Param("u") User user);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM users WHERE id = :id", nativeQuery = true)
    void deleteUserNative(@Param("id") Long id);
}