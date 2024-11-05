package com.project.banking.service.implement;

import com.project.banking.dto.request.AddCardRequest;
import com.project.banking.dto.response.card.CardResponse;
import com.project.banking.entity.Card;
import com.project.banking.entity.Profile;
import com.project.banking.mapper.CardMapper;
import com.project.banking.repository.CardRepository;
import com.project.banking.service.BankAccountService;
import com.project.banking.service.CardService;
import com.project.banking.service.UserService;
import com.project.banking.utils.component.ConverterUtil;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.CardPrincipal;
import com.project.banking.utils.constant.CardStatus;
import com.project.banking.utils.constant.CardType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {
    private final CardRepository cardRepo;
    private final ValidationUtil validation;
    private final CardMapper mapper;

    private final BankAccountService bankAccountService;
    private final UserService userService;
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

        if (bankAccountService.findId(request.getBankAccountId()).getStatus().equals(BankAccountStatus.CLOSED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This bank account is closed");
        }

        Card card = Card.builder()
                .bankAccount(bankAccountService.findId(request.getBankAccountId()))
                .cardType(inputType(request.getCardType()))
                .cardNumber(convert.formatCardNumber(request.getCardNumber()))
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
        Profile currentUser = userService.getByContext().getProfile();
        Card card = findId(id);

        if (!currentUser.getId().equals(card.getBankAccount().getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this card");
        }

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

    @Transactional(readOnly = true)
    @Override
    public Card findId(String id) {
        return cardRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Card not found")
        );
    }

    @Transactional(rollbackFor = Exception.class)
    @Scheduled(cron = "@daily")
    @Override
    public void checkExpCard() {
        List<Card> expiredCard = cardRepo.findExpiredCard(LocalDate.now());

        for (Card card : expiredCard) {
            if (!card.getStatus().equals(CardStatus.EXPIRED)) {
                card.setStatus(CardStatus.EXPIRED);
                card.setUpdatedAt(LocalDateTime.now());
                cardRepo.save(card);
                log.info("Card with ID {} is set to 'EXPIRED'", card.getId());
            }
        }
    }
}
