package com.bank.cardmanagement.controller;

import com.bank.cardmanagement.dto.TransferRequestDto;
import com.bank.cardmanagement.dto.TransferResponseDto;
import com.bank.cardmanagement.service.TransferService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transfers")
@RequiredArgsConstructor
public class TransferController {

    private final TransferService transferService;

    @PostMapping
    public ResponseEntity<TransferResponseDto> transferFunds(
            @Valid @RequestBody TransferRequestDto dto,
            Authentication authentication) {
        String username = authentication.getName();
        TransferResponseDto response = transferService.transferFunds(dto, username);
        return ResponseEntity.ok(response);
    }
}
