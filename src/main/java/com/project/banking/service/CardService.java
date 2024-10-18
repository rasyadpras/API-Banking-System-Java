package com.project.banking.service;

import com.project.banking.dto.request.AddCardRequest;
import com.project.banking.dto.response.card.CardResponse;
import com.project.banking.entity.Card;

public interface CardService {
    CardResponse create(AddCardRequest request);
    CardResponse getById(String id);
    void unblock(String id);

    Card findId(String id);
    void checkExpCard();
}
