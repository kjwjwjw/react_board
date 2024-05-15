package com.junwoo.boardback.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junwoo.boardback.dto.request.board.PostBoardRequestDto;
import com.junwoo.boardback.dto.response.board.PostBoardReponseDto;
import com.junwoo.boardback.service.BoardService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("")
    public ResponseEntity<? super PostBoardReponseDto > postBoard(
        @RequestBody @Valid PostBoardRequestDto RequestBody,
        @AuthenticationPrincipal String email
    ) {
        ResponseEntity<? super PostBoardReponseDto> response = boardService.postBoard(RequestBody, email);
        return response;
    }
    
}
