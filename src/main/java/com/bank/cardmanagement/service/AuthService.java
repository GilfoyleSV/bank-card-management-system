package com.bank.cardmanagement.service;

import com.bank.cardmanagement.dto.JwtAuthResponseDto;
import com.bank.cardmanagement.dto.LoginRequestDto;
import com.bank.cardmanagement.dto.RegisterRequestDto;
import com.bank.cardmanagement.dto.UserResponseDto;

public interface AuthService {

    UserResponseDto register(RegisterRequestDto dto);

    JwtAuthResponseDto login(LoginRequestDto dto);
}
