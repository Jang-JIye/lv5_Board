package com.sparta.lv5_board.controller;

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

    //create
    @PostMapping("/boards")
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(requestDto, userDetails.getUser());
    }

    //readAll
    @GetMapping("/boards")
    public List<BoardResponseDto> getAllBoard(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoards(userDetails.getUser());
    }


    //readacademy
    @GetMapping("/boards/{id}")
    public BoardResponseDto getBoard(@PathVariable Long id,  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.getBoard(id, userDetails.getUser());
    }

    //update
    @PutMapping("/boards/{id}")
    public ResponseEntity<String> updateBoard(@PathVariable Long id, @RequestBody BoardRequestDto requestDto
                                              , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.updateBoard(id, requestDto, userDetails.getUser());

    }

    //delete
    @DeleteMapping("/boards/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.deleteBoard(id, userDetails.getUser());
    }
}