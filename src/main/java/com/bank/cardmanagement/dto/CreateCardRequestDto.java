package com.bank.cardmanagement.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateCardRequestDto {

    @NotNull(message = "ID владельца карты обязателен")
    private Long ownerId;

    @NotNull(message = "Срок действия карты обязателен")
    @Future(message = "Срок действия карты должен быть в будущем")
    private LocalDate expirationDate;

    @NotNull(message = "Начальный баланс обязателен")
    @PositiveOrZero(message = "Баланс не может быть отрицательным")
    private BigDecimal initialBalance;
}
