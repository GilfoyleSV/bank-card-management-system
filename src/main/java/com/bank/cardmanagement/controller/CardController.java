package com.bank.cardmanagement.controller;

import com.bank.cardmanagement.dto.CardResponseDto;
import com.bank.cardmanagement.dto.CreateCardRequestDto;
import com.bank.cardmanagement.dto.UpdateCardStatusDto;
import com.bank.cardmanagement.model.CardStatus;
import com.bank.cardmanagement.service.CardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponseDto> createCard(@Valid @RequestBody CreateCardRequestDto dto) {
        CardResponseDto response = cardService.createCard(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<CardResponseDto>> getAllCards(
            @RequestParam(required = false) CardStatus status,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<CardResponseDto> cards = cardService.getAllCards(status, pageable);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<Page<CardResponseDto>> getUserCards(
            @PathVariable Long userId,
            @RequestParam(required = false) CardStatus status,
            @PageableDefault(size = 10, sort = "createdAt") Pageable pageable) {
        Page<CardResponseDto> cards = cardService.getUserCards(userId, status, pageable);
        return ResponseEntity.ok(cards);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponseDto> getCardById(@PathVariable Long id) {
        CardResponseDto card = cardService.getCardById(id);
        return ResponseEntity.ok(card);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<CardResponseDto> updateCardStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCardStatusDto dto) {
        CardResponseDto card = cardService.updateCardStatus(id, dto);
        return ResponseEntity.ok(card);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }
}
