package com.bank.cardmanagement.mapper;

import com.bank.cardmanagement.dto.CardResponseDto;
import com.bank.cardmanagement.model.Card;
import com.bank.cardmanagement.util.CardUtils;

public class CardMapper {

    private CardMapper() {
    }

    public static CardResponseDto toDto(Card card) {
        if (card == null) {
            return null;
        }
        return CardResponseDto.builder()
                .id(card.getId())
                .maskedCardNumber(CardUtils.maskCardNumber(card.getCardNumber()))
                .ownerId(card.getOwner() != null ? card.getOwner().getId() : null)
                .ownerUsername(card.getOwner() != null ? card.getOwner().getUsername() : null)
                .expirationDate(card.getExpirationDate())
                .status(card.getStatus())
                .balance(card.getBalance())
                .createdAt(card.getCreatedAt())
                .build();
    }
}
