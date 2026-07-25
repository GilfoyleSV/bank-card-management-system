package com.bank.cardmanagement.controller;

import com.bank.cardmanagement.dto.JwtAuthResponseDto;
import com.bank.cardmanagement.dto.LoginRequestDto;
import com.bank.cardmanagement.dto.RegisterRequestDto;
import com.bank.cardmanagement.dto.UserResponseDto;
import com.bank.cardmanagement.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody RegisterRequestDto dto) {
        UserResponseDto user = authService.register(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> login(@Valid @RequestBody LoginRequestDto dto) {
        JwtAuthResponseDto response = authService.login(dto);
        return ResponseEntity.ok(response);
    }
}
