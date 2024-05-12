package com.junwoo.boardback.controller;

import org.springframework.web.bind.annotation.RestController;

import com.junwoo.boardback.dto.response.user.GetSignInUserResponseDto;
import com.junwoo.boardback.service.UserService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<? super GetSignInUserResponseDto> getSignInUser (

      @AuthenticationPrincipal String email
    ) {

        ResponseEntity<? super GetSignInUserResponseDto> response = userService.getSignInUser(email);
        return response;

    }
    
}
