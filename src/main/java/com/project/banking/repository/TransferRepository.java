package com.project.banking.repository;

import com.project.banking.entity.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer, String> {
    @Query(
            value = "SELECT * FROM transfer_transactions t WHERE t.source_account_id = :id",
            nativeQuery = true
    )
    List<Transfer> findAllBySenderAcc(@Param("id") String sourceAccountId);

    @Query(
            value = "SELECT * FROM transfer_transactions t WHERE t.destination_account_id = :id",
            nativeQuery = true
    )
    List<Transfer> findAllByReceiverAcc(@Param("id") String destinationAccountId);

    @Query(value = """
            SELECT * FROM transfer_transactions t WHERE t.source_account_id = :id
            UNION
            SELECT * FROM transfer_transactions t WHERE t.destination_account_id = :id
            """, nativeQuery = true
    )
    List<Transfer> findAllByUserTransfer(@Param("id") String id);
}
