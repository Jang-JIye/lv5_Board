package com.sparta.lv5_board.dto;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class SignoutRequestDto {


    private String userId;

    private String password;

    private boolean admin = false;
    private String adminToken = "";
}
