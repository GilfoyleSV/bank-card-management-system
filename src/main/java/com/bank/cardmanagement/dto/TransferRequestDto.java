package com.bank.cardmanagement.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferRequestDto {

    @NotNull(message = "ID карты списания обязателен")
    private Long fromCardId;

    @NotNull(message = "ID карты зачисления обязателен")
    private Long toCardId;

    @NotNull(message = "Сумма перевода обязательна")
    @Positive(message = "Сумма перевода должна быть больше нуля")
    private BigDecimal amount;
}
