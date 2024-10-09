package com.project.banking.dto.response.profile;

import com.project.banking.utils.constant.CardPrincipal;
import com.project.banking.utils.constant.CardStatus;
import com.project.banking.utils.constant.CardType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardResponseToProfile {
    private String id;
    private CardType cardType;
    private String cardNumber;
    private CardPrincipal principal;
    private CardStatus status;
}
