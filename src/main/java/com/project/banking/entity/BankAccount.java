package com.project.banking.entity;

import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.BankAccountType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "bank_accounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "branch_id", referencedColumnName = "id", nullable = false)
    private Branch branch;

    @ManyToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "id", nullable = false)
    private Profile profile;

    @Column(name = "account_number", nullable = false, unique = true)
    private String accountNumber;

    @Column(name = "bank_account_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankAccountType type;

    @Column(name = "balance", nullable = false, precision = 12, scale = 2)
    private BigDecimal balance;

    @Column(name = "status_bank_account", nullable = false)
    @Enumerated(EnumType.STRING)
    private BankAccountStatus status;

    @OneToMany(mappedBy = "bankAccount")
    private List<Card> cards;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
