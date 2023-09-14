package com.sparta.lv5_board.service;

import com.sparta.lv5_board.dto.BoardAllResponseDto;
import com.sparta.lv5_board.dto.BoardRequestDto;
import com.sparta.lv5_board.dto.BoardResponseDto;
import com.sparta.lv5_board.dto.CommentResponseDto;
import com.sparta.lv5_board.entity.Board;
import com.sparta.lv5_board.entity.LikeBoard;
import com.sparta.lv5_board.entity.User;
import com.sparta.lv5_board.entity.UserRoleEnum;
import com.sparta.lv5_board.repository.BoardRepository;
import com.sparta.lv5_board.repository.CommentRepository;
import com.sparta.lv5_board.repository.LikeBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final LikeBoardRepository likeBoardRepository;


    // Create
    public BoardAllResponseDto createBoard(BoardRequestDto requestDto, User user) {
        Board board = boardRepository.save(new Board(requestDto, user));
        //Entity -> ResponseDto
        return new BoardAllResponseDto(board);
    }


    //ReadAll
    public List<BoardAllResponseDto> getAllBoards(User user) {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtDesc();
        return boardList.stream().map(BoardAllResponseDto::new).collect(Collectors.toList());
    }


    // Read
    public BoardResponseDto getBoard(Long id, User user) {
        return new BoardResponseDto(findBoard(id));
    }


    // Update
    @Transactional
    public ResponseEntity<String> updateBoard(Long id, BoardRequestDto requestDto, User user) {
        Board board = findBoard(id);
        // 해당 메모의 작성자와 현재 로그인한 사용자를 비교하여 작성자가 같지 않으면 예외 발생, 어드민이 아닐 시
        if (user.getRole() == UserRoleEnum.USER && !board.getUser().getUserId().equals(user.getUserId())) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        board.update(requestDto);
        // 수정 성공
        return ResponseEntity.ok("수정 성공!");

    }


    // Delete
    @Transactional
    public ResponseEntity<String> deleteBoard(Long id, User user) {
        Board board = findBoard(id);

        if (user.getRole() == UserRoleEnum.USER && !board.getUser().getUserId().equals(user.getUserId())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("작성자만 삭제할 수 있습니다.");
        }
        // 해당 board와 관련된 모든 comment 삭제
        commentRepository.deleteById(id);

        boardRepository.delete(board);

        return ResponseEntity.ok("삭제 성공!");
    }

    @Transactional
    public ResponseEntity<String> addLike(Long id, User user) {
        Board board = findBoard(id);

        if (!likeBoardRepository.existsByUserAndBoard(user, board)) {
            board.setLikeCnt(board.getLikeCnt() + 1);

            likeBoardRepository.save(new LikeBoard(user, board));

            return ResponseEntity.ok().body("좋야요!");
        } else {
            board.setLikeCnt(board.getLikeCnt() - 1);
            likeBoardRepository.deleteByUserAndBoard(user, board);

            return ResponseEntity.ok().body("좋아요 취소!");
        }
    }

    private Board findBoard(Long id) {
        return boardRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));
    }

}