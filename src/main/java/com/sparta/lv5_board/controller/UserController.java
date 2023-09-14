package com.sparta.lv5_board.controller;

import com.sparta.lv5_board.dto.SignoutRequestDto;
import com.sparta.lv5_board.dto.SignupRequestDto;
import com.sparta.lv5_board.dto.StatusResponseDto;
import com.sparta.lv5_board.security.UserDetailsImpl;
import com.sparta.lv5_board.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;


@Slf4j(topic = "usercontroller")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    @PostMapping("/user/signup")
    public StatusResponseDto signup(@RequestBody SignupRequestDto requestDto, HttpServletResponse httpServletResponse) {
        return userService.signup(requestDto);
    }

    @DeleteMapping("/user/signout")
    public ResponseEntity<String> deleteUser(@RequestBody SignoutRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userService.deleteUser(requestDto, userDetails.getUserId());
    }


}