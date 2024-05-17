package com.junwoo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.junwoo.boardback.dto.request.board.PostBoardRequestDto;
import com.junwoo.boardback.dto.response.board.GetBoardResponseDto;
import com.junwoo.boardback.dto.response.board.PostBoardReponseDto;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super PostBoardReponseDto> postBoard(PostBoardRequestDto dto, String email);


    
}
