package com.junwoo.boardback.service.implement;


import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.junwoo.boardback.dto.request.board.PostBoardRequestDto;
import com.junwoo.boardback.dto.request.board.PostCommentRequestDto;
import com.junwoo.boardback.dto.response.ResponseDto;
import com.junwoo.boardback.dto.response.board.GetBoardResponseDto;
import com.junwoo.boardback.dto.response.board.GetFavoriteListResponseDto;
import com.junwoo.boardback.dto.response.board.PostBoardReponseDto;
import com.junwoo.boardback.dto.response.board.PostCommentResponseDto;
import com.junwoo.boardback.dto.response.board.PutFavoriteResponseDto;
import com.junwoo.boardback.entity.BoardEntity;
import com.junwoo.boardback.entity.CommentEntity;
import com.junwoo.boardback.entity.FavoriteEntity;
import com.junwoo.boardback.entity.ImageEntity;
import com.junwoo.boardback.repository.BoardRepository;
import com.junwoo.boardback.repository.CommentRepository;
import com.junwoo.boardback.repository.FavoriteRepository;
import com.junwoo.boardback.repository.ImageRepository;
import com.junwoo.boardback.repository.UserRepository;
import com.junwoo.boardback.repository.resultSet.GetBoardResultSet;
import com.junwoo.boardback.repository.resultSet.GetFavoriteListResultSet;
import com.junwoo.boardback.service.BoardService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardServiceImplement implements BoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final ImageRepository imageRepository;
    private final CommentRepository commentRepository;
    private final FavoriteRepository favoriteRepository;

    @Override
    public ResponseEntity<? super GetBoardResponseDto> getBoard(Integer boardNumber) {

        GetBoardResultSet resultSet = null;
        List<ImageEntity> imageEntities = new ArrayList<>();

        try {

            resultSet = boardRepository.getBoard(boardNumber);
            if ( resultSet == null) return GetBoardResponseDto.noExistBoard();

            imageEntities = imageRepository.findByBoardNumber(boardNumber);

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
    public ResponseEntity<? super GetFavoriteListResponseDto> getFavoriteList(Integer boardNumber) {

            List<GetFavoriteListResultSet> resultSets = new ArrayList<>();
        
            try {

                boolean existedBoard = boardRepository.existsByBoardNumber(boardNumber);
                if(!existedBoard) return GetFavoriteListResponseDto.noExistBoard();

                resultSets = favoriteRepository.getFavoriteList(boardNumber);

            } catch (Exception exception) {
                exception.printStackTrace();
                return ResponseDto.databaseError();
            }

        return GetFavoriteListResponseDto.success(resultSets);
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

    @Override
    public ResponseEntity<? super PostCommentResponseDto> postComment(PostCommentRequestDto dto, Integer boardNumber  ,String email) {
        
       

        try {

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if(boardEntity == null) return PostCommentResponseDto.noExistBoard();

            boolean existedUser = boardRepository.existsByBoardNumber(boardNumber);
            if(!existedUser) return PostCommentResponseDto.noExistUser();

            CommentEntity commentEntity = new CommentEntity(dto, boardNumber, email);
            commentRepository.save(commentEntity);

            boardEntity.increaseCommentCount();
            boardRepository.save(boardEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
        }

        return PostCommentResponseDto.success();
    }

  

    @Override
    public ResponseEntity<? super PutFavoriteResponseDto> putFavorite(Integer boardNumber, String email) {
      
        try {

            boolean existedUser = userRepository.existsByEmail(email);
            if(!existedUser) return PutFavoriteResponseDto.noExistUser();

            BoardEntity boardEntity = boardRepository.findByBoardNumber(boardNumber);
            if (boardEntity == null) return PutFavoriteResponseDto.noExistBoard();

            FavoriteEntity favoriteEntity = favoriteRepository.findByBoardNumberAndUserEmail(boardNumber, email);
            if(favoriteEntity == null) {
                favoriteEntity = new FavoriteEntity(email, boardNumber);
                favoriteRepository.save(favoriteEntity);
                boardEntity.increaseFavoriteCount();
            } 
            else {
                favoriteRepository.delete(favoriteEntity);
                boardEntity.decreaseFavoriteCount();
            }

            boardRepository.save(boardEntity);
            
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseDto.databaseError();
          
        }

        return PutFavoriteResponseDto.success();
    }

  
  
    
}
