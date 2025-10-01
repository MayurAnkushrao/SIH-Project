package com.sih.SIHbackend.repository;

import com.sih.SIHbackend.entity.User;
import com.sih.SIHbackend.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    // ✅ Method to find user by email
    Optional<User> findByEmail(String email);
    
    // ✅ Method to check if user exists by email
    boolean existsByEmail(String email);
    
    // Additional useful methods for your application
    List<User> findByRole(UserRole role);
    
    List<User> findByOrganization(String organization);
    
    @Query("SELECT u FROM User u WHERE LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<User> findByNameContainingIgnoreCase(@Param("name") String name);
    
    @Query("SELECT u FROM User u WHERE u.role = :role AND u.organization = :organization")
    List<User> findByRoleAndOrganization(@Param("role") UserRole role, @Param("organization") String organization);
    
    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    Long countByRole(@Param("role") UserRole role);
    
    // Find users created after a specific date
    @Query("SELECT u FROM User u WHERE u.createdAt >= :fromDate ORDER BY u.createdAt DESC")
    List<User> findRecentUsers(@Param("fromDate") java.time.LocalDateTime fromDate);
    
    // Check if user exists by email (alternative implementation using @Query)
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email")
    boolean existsByEmailQuery(@Param("email") String email);
}
