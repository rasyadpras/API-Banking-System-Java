package com.project.banking.controller;

import com.project.banking.dto.request.AddCardRequest;
import com.project.banking.dto.response.card.CardResponse;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.service.CardService;
import com.project.banking.utils.constant.APIUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.CARD_API)
public class CardController {
    private final CardService cardService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<CardResponse>> addCard(@RequestBody AddCardRequest request) {
        CardResponse card = cardService.create(request);
        SuccessResponse<CardResponse> response = SuccessResponse.<CardResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(card)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @GetMapping(
            path = APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<CardResponse>> getCardById(@PathVariable String id) {
        CardResponse card = cardService.getById(id);
        SuccessResponse<CardResponse> response = SuccessResponse.<CardResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(card)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @PatchMapping(
            path = APIUrl.PATH_ID + APIUrl.PATH_UNBLOCK,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<String>> unblockCard(@PathVariable String id) {
        cardService.unblock(id);
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data("Card has been activated")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
