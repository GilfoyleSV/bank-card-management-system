package com.bank.cardmanagement.service.impl;

import com.bank.cardmanagement.dto.TransferRequestDto;
import com.bank.cardmanagement.dto.TransferResponseDto;
import com.bank.cardmanagement.exception.BadRequestException;
import com.bank.cardmanagement.exception.ResourceNotFoundException;
import com.bank.cardmanagement.model.Card;
import com.bank.cardmanagement.model.CardStatus;
import com.bank.cardmanagement.model.User;
import com.bank.cardmanagement.repository.CardRepository;
import com.bank.cardmanagement.repository.UserRepository;
import com.bank.cardmanagement.service.TransferService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TransferServiceImpl implements TransferService {

    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TransferResponseDto transferFunds(TransferRequestDto dto, String username) {
        if (dto.getFromCardId().equals(dto.getToCardId())) {
            throw new BadRequestException("Нельзя перевести средства на ту же самую карту");
        }

        User currentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь " + username + " не найден"));

        Card fromCard = cardRepository.findById(dto.getFromCardId())
                .orElseThrow(() -> new ResourceNotFoundException("Карта списания " + dto.getFromCardId() + " не найдена"));

        Card toCard = cardRepository.findById(dto.getToCardId())
                .orElseThrow(() -> new ResourceNotFoundException("Карта зачисления " + dto.getToCardId() + " не найдена"));

        if (!fromCard.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Вы можете совершать переводы только со своих собственных карт");
        }

        if (!toCard.getOwner().getId().equals(currentUser.getId())) {
            throw new BadRequestException("Вы можете совершать переводы только между своими собственными картами");
        }

        if (fromCard.getStatus() != CardStatus.ACTIVE) {
            throw new BadRequestException("Карта списания заблокирована или неактивна");
        }

        if (toCard.getStatus() != CardStatus.ACTIVE) {
            throw new BadRequestException("Карта зачисления заблокирована или неактивна");
        }

        if (fromCard.getExpirationDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Срок действия карты списания истек");
        }

        if (toCard.getExpirationDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("Срок действия карты зачисления истек");
        }

        if (fromCard.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new BadRequestException("Недостаточно средств на карте списания");
        }

        fromCard.setBalance(fromCard.getBalance().subtract(dto.getAmount()));
        toCard.setBalance(toCard.getBalance().add(dto.getAmount()));

        cardRepository.save(fromCard);
        cardRepository.save(toCard);

        return TransferResponseDto.builder()
                .fromCardId(fromCard.getId())
                .toCardId(toCard.getId())
                .amount(dto.getAmount())
                .fromCardBalance(fromCard.getBalance())
                .status("SUCCESS")
                .timestamp(LocalDateTime.now())
                .build();
    }
}
