package com.junwoo.boardback.controller;

import org.springframework.web.bind.annotation.RestController;

import com.junwoo.boardback.dto.request.auth.SignInRequestDto;
import com.junwoo.boardback.dto.request.auth.SignUpRequestDto;
import com.junwoo.boardback.dto.response.auth.SignInResponseDto;
import com.junwoo.boardback.dto.response.auth.SignUpResponseDto;
import com.junwoo.boardback.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;



@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    
    @PostMapping("/sign-up")
    public ResponseEntity<? super SignUpResponseDto> signup( 
        @RequestBody @Valid SignUpRequestDto requestBody) 
        {
            ResponseEntity<? super SignUpResponseDto> response = authService.signup(requestBody);
            return response;
        }

    @PostMapping("/sign-in")
    public ResponseEntity<? super SignInResponseDto> signin(
        @RequestBody @Valid SignInRequestDto requestBody ) 
    {
            ResponseEntity<? super SignInResponseDto> response = authService.signin(requestBody);
            return response;
        
    }
    

    
}
