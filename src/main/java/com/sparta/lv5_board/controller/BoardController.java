package com.sparta.lv5_board.controller;

import com.sparta.lv5_board.dto.BoardAllResponseDto;
import com.sparta.lv5_board.dto.BoardRequestDto;
import com.sparta.lv5_board.dto.BoardResponseDto;
import com.sparta.lv5_board.security.UserDetailsImpl;
import com.sparta.lv5_board.service.BoardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

// Create
    @PostMapping("/boards")
    public BoardAllResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

// ReadAll
    @GetMapping("/boards")
    public List<BoardAllResponseDto> getAllBoard(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getAllBoards(userDetails.getUser());
    }


// Read
    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoard(id, userDetails.getUser());
    }

// Update
    @PutMapping("/boards/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto
                                              , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());

    }


// Delete
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }

    //like
    @PostMapping("/boards/{id}/likes")
    public ResponseEntity<String> addLike(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails ) {
        return boardService.addLike(id, userDetails.getUser());
    }
}