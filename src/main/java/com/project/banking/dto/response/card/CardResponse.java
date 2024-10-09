package com.project.banking.dto.response.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.CardPrincipal;
import com.project.banking.utils.constant.CardStatus;
import com.project.banking.utils.constant.CardType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponse {
    private String id;
    private BankAccountResponseToCard bankAccount;
    private CardType cardType;
    private String cardNumber;
    private CardPrincipal principal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yy")
    private LocalDate validThru;
    private String cvv;
    private CardStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime activeDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
