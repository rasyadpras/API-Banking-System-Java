package com.project.banking.repository;

import com.project.banking.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    @Query(value = "SELECT * FROM roles r WHERE r.role = :role", nativeQuery = true)
    Optional<Role> findByRole(@Param("role") String role);
}
