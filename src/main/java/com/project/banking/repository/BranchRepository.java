package com.project.banking.repository;

import com.project.banking.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    @Query(
            value = "SELECT * FROM bank_branches b WHERE LOWER(b.region) LIKE LOWER(CONCAT('%', :region, '%'))",
            nativeQuery = true
    )
    List<Branch> findAllByRegion(@Param("region") String region);

    @Query(
            value = "SELECT EXISTS(SELECT 1 FROM bank_branches b WHERE b.branch_code = :branchCode)",
            nativeQuery = true
    )
    boolean existsByBranchCode(@Param("branchCode") String branchCode);
}
