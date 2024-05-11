package com.junwoo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.junwoo.boardback.dto.response.auth.SignInResponseDto;
import com.junwoo.boardback.dto.response.auth.SignUpResponseDto;
import com.junwoo.boardback.dto.request.auth.SignInRequestDto;
import com.junwoo.boardback.dto.request.auth.SignUpRequestDto;

public interface AuthService {
    
     ResponseEntity<? super SignUpResponseDto> signup(SignUpRequestDto dto);
     ResponseEntity<? super SignInResponseDto> signin(SignInRequestDto dto);
}
