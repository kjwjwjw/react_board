package com.junwoo.boardback.service.implement;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.junwoo.boardback.dto.request.board.PostBoardRequestDto;
import com.junwoo.boardback.dto.response.ResponseDto;
import com.junwoo.boardback.dto.response.board.GetBoardResponseDto;
import com.junwoo.boardback.dto.response.board.PostBoardReponseDto;
import com.junwoo.boardback.entity.BoardEntity;
import com.junwoo.boardback.entity.ImageEntity;
import com.junwoo.boardback.repository.BoardRepository;
import com.junwoo.boardback.repository.ImageRepository;
import com.junwoo.boardback.repository.UserRepository;
import com.junwoo.boardback.repository.resultSet.GetBoardResultSet;
import com.junwoo.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {

            resultSet = boardRepository.getBoard(boardNumber);
            if ( resultSet == null) return GetBoardResponseDto.noExistBoard();

            imageEntities = imageRepository.finByBoardNumber(boardNumber);

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            boardEntity.increaseViewCount();
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return GetBoardResponseDto.success(resultSet, imageEntities);
    }
    
    @Override
    public ResponseEntity<? super PostBoardReponseDto> postBoard(PostBoardRequestDto dto, String email) {

        try {

            boolean existedEmail = userRepository.existsByEmail(email);
            if (!existedEmail) return PostBoardReponseDto.notExistUser();

            BoardEntity boardEntity = new BoardEntity(dto, email);
            boardRepository.save(boardEntity);

            int boardNumber = boardEntity.getBoardNumber();

            List<String> boardImageList = dto.getBoardImageList();
            List<ImageEntity> imageEntities = new ArrayList<>();

            for( String image : boardImageList) {
                ImageEntity imageEntity = new ImageEntity(boardNumber, image);
                imageEntities.add(imageEntity);
            }

            imageRepository.saveAll(imageEntities);

        }catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();

        }
        return PostBoardReponseDto.success();
      
    }

  
    
}
