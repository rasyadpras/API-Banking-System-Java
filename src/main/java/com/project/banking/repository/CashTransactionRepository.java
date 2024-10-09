package com.project.banking.repository;

import com.project.banking.entity.CashTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CashTransactionRepository extends JpaRepository<CashTransaction, String> {
    @Query(
            value = "SELECT * FROM cash_transaction t WHERE t.bank_account_id = :id",
            nativeQuery = true
    )
    List<CashTransaction> findAllByUserTransaction(@Param("id") List<String> id);
}
