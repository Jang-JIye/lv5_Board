package com.sparta.lv5_board.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommentRequestDto {
    private Long boardId;
    private String comment;
}
