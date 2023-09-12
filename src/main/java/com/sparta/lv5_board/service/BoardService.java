package com.sparta.lv5_board.service;

import com.sparta.lv5_board.dto.BoardRequestDto;
import com.sparta.lv5_board.dto.BoardResponseDto;
import com.sparta.lv5_board.entity.Board;
import com.sparta.lv5_board.entity.User;
import com.sparta.lv5_board.entity.UserRoleEnum;
import com.sparta.lv5_board.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, User user) {
        //RequestDto -> Entity
        Board board = boardRepository.save(new Board(requestDto, user));

        //Entity -> ResponseDto
        return new BoardResponseDto(board);
    }


    public List<BoardResponseDto> getBoards(User user) {
        //DB 조회
        UserRoleEnum userRoleEnum = user.getRole();
        List<Board> boardList;

        if(userRoleEnum == UserRoleEnum.USER) {
            boardList = boardRepository.findAllByUserOrderByModifiedAtDesc(user);  // 유저권한, 현재 유저의 메모만 조회
        } else {
            boardList = boardRepository.findAllByOrderByModifiedAtDesc();  // 관리자권한, 모든 메모 조회
        }
        return boardList.stream().map(BoardResponseDto::new).toList();
    }


    public BoardResponseDto getBoard(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);

        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생
        if (!board.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 메모에 접근 권한이 없습니다.");
        }
        return new BoardResponseDto(board);
    }




    @Transactional
    public ResponseEntity<String> updateBoard(Long id, BoardRequestDto requestDto, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);
        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생
        if (!board.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 메모를 수정할 권한이 없습니다.");
        }
        // memo 수정
        board.update(requestDto);
        // 수정할 때 비밀번호 검증
        if (user.getPassword().equals(board.getPassword())) {
            // 수정 성공
            return ResponseEntity.ok("수정 성공!");
        } else {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }


    public ResponseEntity<String> deleteBoard(Long id, User user) {
        // 해당 메모가 DB에 존재하는지 확인
        Board board = findBoard(id);

        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생
        if (!board.getUsername().equals(user.getUsername())) {
            throw new IllegalArgumentException("해당 메모를 삭제할 권한이 없습니다.");
        }
        // memo 삭제
        boardRepository.delete(board);
        return ResponseEntity.status(HttpStatus.OK)
                .body("{\"status\": 200, \"msg\": \"삭제 성공\"}");
    }


    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("선택한 메모는 존재하지 않습니다."));
    }


}