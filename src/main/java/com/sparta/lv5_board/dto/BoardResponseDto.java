package com.sparta.lv5_board.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.lv5_board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class BoardResponseDto {
    private String title;//제목
    private String username;//작성자명
    private String contents;//작성 내용
    private LocalDateTime createdAt; // 작성시간
    private Integer likeCnt;

    private List<CommentResponseDto> commentResponseDtoList;

    public BoardResponseDto(Board board) {
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.likeCnt = board.getLikeCnt();
        this.commentResponseDtoList = board.getComments().stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }
//
//    public BoardResponseDto(Board board, boolean getComments) {
//        this.title = board.getTitle();
//        this.username = board.getUsername();
//        this.contents = board.getContents();
//        this.createdAt = board.getCreatedAt();
//        this.likeCnt = board.getLikeCnt();
//        this.commentResponseDtoList = board.getComments().stream()
//                .map(CommentResponseDto::new)
//                .collect(Collectors.toList());
//    }
}

