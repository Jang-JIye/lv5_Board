package com.sparta.lv5_board.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class StatusResponseDto {

    private String msg;
    private Integer statusCode;

    public StatusResponseDto(String msg, Integer statusCode) {
        this.msg = msg;
        this.statusCode = statusCode;
    }
}
