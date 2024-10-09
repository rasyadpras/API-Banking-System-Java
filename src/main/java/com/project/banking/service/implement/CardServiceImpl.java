package com.project.banking.service.implement;

import com.project.banking.dto.request.AddCardRequest;
import com.project.banking.dto.response.card.CardResponse;
import com.project.banking.entity.Card;
import com.project.banking.mapper.CardMapper;
import com.project.banking.repository.CardRepository;
import com.project.banking.service.BankAccountService;
import com.project.banking.service.CardService;
import com.project.banking.utils.component.ConverterUtil;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.CardPrincipal;
import com.project.banking.utils.constant.CardStatus;
import com.project.banking.utils.constant.CardType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepo;
    private final ValidationUtil validation;
    private final CardMapper mapper;

    private final BankAccountService bankAccountService;
    private final ConverterUtil convert;

    private CardType inputType(String type) {
        return switch (type.toLowerCase()) {
            case "debit" -> CardType.DEBIT;
            case "credit" -> CardType.CREDIT;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card type must be 'debit' or 'credit'");
        };
    }

    private CardPrincipal inputPrincipal(String principal) {
        return switch (principal.toLowerCase()) {
            case "visa" -> CardPrincipal.VISA;
            case "mastercard" -> CardPrincipal.MASTERCARD;
            case "gpn" -> CardPrincipal.GPN;
            case "jcb" -> CardPrincipal.JCB;
            case "union" -> CardPrincipal.UNION_PAY;
            case "amex" -> CardPrincipal.AMERICAN_EXPRESS;
            case "none" -> CardPrincipal.NO_PRINCIPAL;
            case "other" -> CardPrincipal.OTHER;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Card principal must be 'visa', 'mastercard', 'gpn', 'jcb', 'union', 'amex', 'none', or 'other'"
            );
        };
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CardResponse create(AddCardRequest request) {
        validation.validate(request);
        Card card = Card.builder()
                .bankAccount(bankAccountService.findId(request.getBankAccountId()))
                .cardType(inputType(request.getCardType()))
                .cardNumber(request.getCardNumber())
                .principal(inputPrincipal(request.getPrincipalCard()))
                .expiredDate(convert.convertToExpiryDate(request.getExpirationDate()))
                .cvv(request.getCvv())
                .status(CardStatus.ACTIVE)
                .activeDate(LocalDateTime.now())
                .build();
        cardRepo.saveAndFlush(card);
        return mapper.toCardResponse(card);
    }

    @Transactional(readOnly = true)
    @Override
    public CardResponse getById(String id) {
        Card card = findId(id);
        return mapper.toCardResponse(card);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void unblock(String id) {
        Card card = findId(id);
        if (card.getStatus().equals(CardStatus.ACTIVE)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Card is already active");
        }
        if (card.getStatus().equals(CardStatus.EXPIRED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Your card is expired");
        }
        card.setStatus(CardStatus.ACTIVE);
        card.setUpdatedAt(LocalDateTime.now());
        cardRepo.save(card);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Card findId(String id) {
        return cardRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        );
    }
}
