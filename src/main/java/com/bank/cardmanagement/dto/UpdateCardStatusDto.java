package com.bank.cardmanagement.dto;

import com.bank.cardmanagement.model.CardStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateCardStatusDto {

    @NotNull(message = "Статус карты обязателен")
    private CardStatus status;
}
