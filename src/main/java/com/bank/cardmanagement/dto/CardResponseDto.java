package com.bank.cardmanagement.dto;

import com.bank.cardmanagement.model.CardStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CardResponseDto {

    private Long id;
    private String maskedCardNumber;
    private Long ownerId;
    private String ownerUsername;
    private LocalDate expirationDate;
    private CardStatus status;
    private BigDecimal balance;
    private LocalDateTime createdAt;
}
