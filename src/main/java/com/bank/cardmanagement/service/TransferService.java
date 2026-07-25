package com.bank.cardmanagement.service;

import com.bank.cardmanagement.dto.TransferRequestDto;
import com.bank.cardmanagement.dto.TransferResponseDto;

public interface TransferService {

    TransferResponseDto transferFunds(TransferRequestDto dto, String username);
}
