package com.bank.cardmanagement.service;

import com.bank.cardmanagement.dto.CardResponseDto;
import com.bank.cardmanagement.dto.CreateCardRequestDto;
import com.bank.cardmanagement.dto.UpdateCardStatusDto;
import com.bank.cardmanagement.model.CardStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CardService {

    CardResponseDto createCard(CreateCardRequestDto dto);

    Page<CardResponseDto> getAllCards(CardStatus status, Pageable pageable);

    Page<CardResponseDto> getUserCards(Long ownerId, CardStatus status, Pageable pageable);

    CardResponseDto getCardById(Long cardId);

    CardResponseDto updateCardStatus(Long cardId, UpdateCardStatusDto dto);

    void deleteCard(Long cardId);
}
