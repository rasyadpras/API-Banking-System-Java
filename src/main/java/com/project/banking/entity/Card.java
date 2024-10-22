package com.project.banking.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.CardPrincipal;
import com.project.banking.utils.constant.CardStatus;
import com.project.banking.utils.constant.CardType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cards")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id", nullable = false)
    private BankAccount bankAccount;

    @Column(name = "card_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "card_number", nullable = false, unique = true, length = 16)
    private String cardNumber;

    @Column(name = "principal", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardPrincipal principal;

    @Column(name = "expired_date", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yy")
    private LocalDate expiredDate;

    @Column(name = "cvv", nullable = false, length = 4)
    private String cvv;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardStatus status;

    @Column(name = "active_date", nullable = false)
    private LocalDateTime activeDate;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}
