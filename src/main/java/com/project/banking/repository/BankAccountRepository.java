package com.project.banking.repository;

import com.project.banking.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
    @Query(
            value = "SELECT * FROM bank_accounts a WHERE a.profile_id = :id",
            nativeQuery = true
    )
    List<BankAccount> findByProfileId(@Param("id") String profileId);

    @Query(
            value = "SELECT * FROM bank_accounts a WHERE a.account_number = :accountNumber",
            nativeQuery = true
    )
    Optional<BankAccount> findByAccountNumber(@Param("accountNumber") String accountNumber);
}
