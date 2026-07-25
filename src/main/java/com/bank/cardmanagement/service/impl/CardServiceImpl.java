package com.bank.cardmanagement.service.impl;

import com.bank.cardmanagement.dto.CardResponseDto;
import com.bank.cardmanagement.dto.CreateCardRequestDto;
import com.bank.cardmanagement.dto.UpdateCardStatusDto;
import com.bank.cardmanagement.exception.ResourceNotFoundException;
import com.bank.cardmanagement.mapper.CardMapper;
import com.bank.cardmanagement.model.Card;
import com.bank.cardmanagement.model.CardStatus;
import com.bank.cardmanagement.model.User;
import com.bank.cardmanagement.repository.CardRepository;
import com.bank.cardmanagement.repository.UserRepository;
import com.bank.cardmanagement.service.CardService;
import com.bank.cardmanagement.util.CardUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CardResponseDto createCard(CreateCardRequestDto dto) {
        User owner = userRepository.findById(dto.getOwnerId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь с ID " + dto.getOwnerId() + " не найден"));

        String cardNumber;
        do {
            cardNumber = CardUtils.generateCardNumber();
        } while (cardRepository.existsByCardNumber(cardNumber));

        Card card = Card.builder()
                .cardNumber(cardNumber)
                .owner(owner)
                .expirationDate(dto.getExpirationDate())
                .status(CardStatus.ACTIVE)
                .balance(dto.getInitialBalance())
                .build();

        Card savedCard = cardRepository.save(card);
        return CardMapper.toDto(savedCard);
    }

    @Override
    public Page<CardResponseDto> getAllCards(CardStatus status, Pageable pageable) {
        Page<Card> cards;
        if (status != null) {
            cards = cardRepository.findByStatus(status, pageable);
        } else {
            cards = cardRepository.findAll(pageable);
        }
        return cards.map(CardMapper::toDto);
    }

    @Override
    public Page<CardResponseDto> getUserCards(Long ownerId, CardStatus status, Pageable pageable) {
        if (!userRepository.existsById(ownerId)) {
            throw new ResourceNotFoundException("Пользователь с ID " + ownerId + " не найден");
        }

        Page<Card> cards;
        if (status != null) {
            cards = cardRepository.findByOwnerIdAndStatus(ownerId, status, pageable);
        } else {
            cards = cardRepository.findByOwnerId(ownerId, pageable);
        }
        return cards.map(CardMapper::toDto);
    }

    @Override
    public CardResponseDto getCardById(Long cardId) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Карта с ID " + cardId + " не найдена"));
        return CardMapper.toDto(card);
    }

    @Override
    @Transactional
    public CardResponseDto updateCardStatus(Long cardId, UpdateCardStatusDto dto) {
        Card card = cardRepository.findById(cardId)
                .orElseThrow(() -> new ResourceNotFoundException("Карта с ID " + cardId + " не найдена"));

        card.setStatus(dto.getStatus());
        Card updatedCard = cardRepository.save(card);
        return CardMapper.toDto(updatedCard);
    }

    @Override
    @Transactional
    public void deleteCard(Long cardId) {
        if (!cardRepository.existsById(cardId)) {
            throw new ResourceNotFoundException("Карта с ID " + cardId + " не найдена");
        }
        cardRepository.deleteById(cardId);
    }
}
