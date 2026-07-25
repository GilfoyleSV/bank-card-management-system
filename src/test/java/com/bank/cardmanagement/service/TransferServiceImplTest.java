package com.bank.cardmanagement.service;

import com.bank.cardmanagement.dto.TransferRequestDto;
import com.bank.cardmanagement.dto.TransferResponseDto;
import com.bank.cardmanagement.exception.BadRequestException;
import com.bank.cardmanagement.model.Card;
import com.bank.cardmanagement.model.CardStatus;
import com.bank.cardmanagement.model.Role;
import com.bank.cardmanagement.model.User;
import com.bank.cardmanagement.repository.CardRepository;
import com.bank.cardmanagement.repository.UserRepository;
import com.bank.cardmanagement.service.impl.TransferServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TransferServiceImpl transferService;

    private User owner;
    private Card fromCard;
    private Card toCard;

    @BeforeEach
    void setUp() {
        owner = User.builder()
                .id(1L)
                .username("john_doe")
                .role(Role.ROLE_USER)
                .build();

        fromCard = Card.builder()
                .id(10L)
                .cardNumber("1111222233334444")
                .owner(owner)
                .expirationDate(LocalDate.now().plusYears(1))
                .status(CardStatus.ACTIVE)
                .balance(new BigDecimal("1000.00"))
                .build();

        toCard = Card.builder()
                .id(20L)
                .cardNumber("5555666677778888")
                .owner(owner)
                .expirationDate(LocalDate.now().plusYears(1))
                .status(CardStatus.ACTIVE)
                .balance(new BigDecimal("200.00"))
                .build();
    }

    @Test
    @DisplayName("Should execute transfer successfully")
    void testTransferSuccess() {
        TransferRequestDto dto = TransferRequestDto.builder()
                .fromCardId(10L)
                .toCardId(20L)
                .amount(new BigDecimal("300.00"))
                .build();

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(owner));
        when(cardRepository.findById(10L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findById(20L)).thenReturn(Optional.of(toCard));

        TransferResponseDto response = transferService.transferFunds(dto, "john_doe");

        assertNotNull(response);
        assertEquals("SUCCESS", response.getStatus());
        assertEquals(new BigDecimal("700.00"), fromCard.getBalance());
        assertEquals(new BigDecimal("500.00"), toCard.getBalance());
        verify(cardRepository, times(2)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw BadRequestException on insufficient funds")
    void testTransferInsufficientFunds() {
        TransferRequestDto dto = TransferRequestDto.builder()
                .fromCardId(10L)
                .toCardId(20L)
                .amount(new BigDecimal("5000.00"))
                .build();

        when(userRepository.findByUsername("john_doe")).thenReturn(Optional.of(owner));
        when(cardRepository.findById(10L)).thenReturn(Optional.of(fromCard));
        when(cardRepository.findById(20L)).thenReturn(Optional.of(toCard));

        assertThrows(BadRequestException.class, () -> transferService.transferFunds(dto, "john_doe"));
        verify(cardRepository, never()).save(any(Card.class));
    }
}
