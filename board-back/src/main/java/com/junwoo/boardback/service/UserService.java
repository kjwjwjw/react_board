package com.junwoo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.junwoo.boardback.dto.response.user.GetSignInUserResponseDto;

public interface UserService {

    ResponseEntity<? super GetSignInUserResponseDto> getSignInUser(String email);
    
}
