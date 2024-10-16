package com.project.banking.dto.response.bankacc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.CardPrincipal;
import com.project.banking.utils.constant.CardStatus;
import com.project.banking.utils.constant.CardType;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponseToBankAccount {
    private String cardId;
    private CardType cardType;
    private String cardNumber;
    private CardPrincipal principal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yy")
    private LocalDate validThru;
    private CardStatus cardStatus;
}
