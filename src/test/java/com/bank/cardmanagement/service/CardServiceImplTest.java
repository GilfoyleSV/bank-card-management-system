package com.bank.cardmanagement.service;

import com.bank.cardmanagement.dto.CardResponseDto;
import com.bank.cardmanagement.dto.CreateCardRequestDto;
import com.bank.cardmanagement.exception.ResourceNotFoundException;
import com.bank.cardmanagement.model.Card;
import com.bank.cardmanagement.model.CardStatus;
import com.bank.cardmanagement.model.Role;
import com.bank.cardmanagement.model.User;
import com.bank.cardmanagement.repository.CardRepository;
import com.bank.cardmanagement.repository.UserRepository;
import com.bank.cardmanagement.service.impl.CardServiceImpl;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardServiceImplTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CardServiceImpl cardService;

    private User testUser;
    private Card testCard;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .username("testuser")
                .email("test@example.com")
                .role(Role.ROLE_USER)
                .build();

        testCard = Card.builder()
                .id(100L)
                .cardNumber("1234567890123456")
                .owner(testUser)
                .expirationDate(LocalDate.now().plusYears(2))
                .status(CardStatus.ACTIVE)
                .balance(new BigDecimal("1000.00"))
                .build();
    }

    @Test
    @DisplayName("Should create card successfully")
    void testCreateCardSuccess() {
        CreateCardRequestDto dto = CreateCardRequestDto.builder()
                .ownerId(1L)
                .expirationDate(LocalDate.now().plusYears(2))
                .initialBalance(new BigDecimal("500.00"))
                .build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(cardRepository.existsByCardNumber(any())).thenReturn(false);
        when(cardRepository.save(any(Card.class))).thenReturn(testCard);

        CardResponseDto response = cardService.createCard(dto);

        assertNotNull(response);
        assertEquals("**** **** **** 3456", response.getMaskedCardNumber());
        assertEquals(1L, response.getOwnerId());
        verify(cardRepository, times(1)).save(any(Card.class));
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when user not found on card creation")
    void testCreateCardUserNotFound() {
        CreateCardRequestDto dto = CreateCardRequestDto.builder()
                .ownerId(999L)
                .expirationDate(LocalDate.now().plusYears(2))
                .initialBalance(new BigDecimal("500.00"))
                .build();

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> cardService.createCard(dto));
        verify(cardRepository, never()).save(any(Card.class));
    }

    @Test
    @DisplayName("Should return card by ID successfully")
    void testGetCardByIdSuccess() {
        when(cardRepository.findById(100L)).thenReturn(Optional.of(testCard));

        CardResponseDto response = cardService.getCardById(100L);

        assertNotNull(response);
        assertEquals(100L, response.getId());
        assertEquals("**** **** **** 3456", response.getMaskedCardNumber());
    }
}
