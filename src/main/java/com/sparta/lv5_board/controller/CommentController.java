package com.sparta.lv5_board.controller;

import com.sparta.lv5_board.dto.CommentRequestDto;
import com.sparta.lv5_board.dto.CommentResponseDto;
import com.sparta.lv5_board.dto.StatusResponseDto;
import com.sparta.lv5_board.security.UserDetailsImpl;
import com.sparta.lv5_board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // create-Comment
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        CommentResponseDto result = commentService.createComment(commentRequestDto, userDetails.getUser());
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    // update-Comment
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        var result = commentService.updateComment(commentId, commentRequestDto, userDetails.getUser());
        return ResponseEntity.ok(result);
    }

    // delete-Comment
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<StatusResponseDto> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return ResponseEntity.ok(commentService.deleteComment(commentId, userDetails.getUser()));
    }

    //like
    @PostMapping("/comments/{commentId}/likes")
    public ResponseEntity<String> addLike(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.addLike(commentId, userDetails.getUser());
    }
}
