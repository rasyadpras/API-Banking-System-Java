package com.project.banking.repository;

import com.project.banking.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, String> {
    @Query(value = "SELECT * FROM cards c WHERE c.expired_date < :date", nativeQuery = true)
    List<Card> findExpiredCard(@Param("date") LocalDate date);
}
