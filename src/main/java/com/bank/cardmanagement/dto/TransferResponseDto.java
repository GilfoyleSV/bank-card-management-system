package com.bank.cardmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferResponseDto {

    private Long fromCardId;
    private Long toCardId;
    private BigDecimal amount;
    private BigDecimal fromCardBalance;
    private String status;
    private LocalDateTime timestamp;
}
