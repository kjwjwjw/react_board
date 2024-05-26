package com.junwoo.boardback.service;

import org.springframework.http.ResponseEntity;

import com.junwoo.boardback.dto.request.board.PostBoardRequestDto;
import com.junwoo.boardback.dto.request.board.PostCommentRequestDto;
import com.junwoo.boardback.dto.response.board.GetBoardResponseDto;
import com.junwoo.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.junwoo.boardback.dto.response.board.PostBoardReponseDto;
import com.junwoo.boardback.dto.response.board.PostCommentResponseDto;
import com.junwoo.boardback.dto.response.board.PutFavoriteResponseDto;

public interface BoardService {
    ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber);
    ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber);
    ResponseEntity<? super PostBoardReponseDto> postBoard(PostBoardRequestDto dto, String email);

    ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber ,String email);

    ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email);


    
}
