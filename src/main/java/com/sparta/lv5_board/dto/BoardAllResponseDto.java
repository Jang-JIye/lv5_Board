package com.sparta.lv5_board.dto;

import com.sparta.lv5_board.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BoardAllResponseDto {

    private String title;//제목
    private String username;//작성자명
    private String contents;//작성 내용
    private LocalDateTime createdAt; // 작성시간
    private Integer likeCnt;

    public BoardAllResponseDto(Board board) {
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.likeCnt = board.getLikeCnt();

    }
}
