package com.project.banking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "transfer_transactions")
public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "source_account_id", referencedColumnName = "id", nullable = false)
    private BankAccount sourceAccount;

    @ManyToOne
    @JoinColumn(name = "destination_account_id", referencedColumnName = "id", nullable = false)
    private BankAccount destinationAccount;

    @Column(name = "amount", nullable = false, precision = 9, scale = 2)
    private BigDecimal amount;

    @Column(name = "transaction_date", nullable = false)
    private LocalDateTime transactionDate;
}
