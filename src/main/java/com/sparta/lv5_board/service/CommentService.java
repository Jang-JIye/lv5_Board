package com.sparta.lv5_board.service;


import com.sparta.lv5_board.dto.CommentRequestDto;
import com.sparta.lv5_board.dto.CommentResponseDto;
import com.sparta.lv5_board.dto.StatusResponseDto;
import com.sparta.lv5_board.entity.Board;
import com.sparta.lv5_board.entity.Comment;
import com.sparta.lv5_board.entity.User;
import com.sparta.lv5_board.entity.UserRoleEnum;
import com.sparta.lv5_board.repository.BoardRepository;
import com.sparta.lv5_board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // Comment 작성
    @Transactional
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(commentRequestDto.getBoardId())
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = new Comment(user.getUsername(), board, commentRequestDto);
        Comment saveComment = commentRepository.save(comment);

        return new CommentResponseDto(saveComment);
    }

    // Comment 수정
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("수정할 댓글이 존재하지 않습니다."));

        if (user.getRole().equals(UserRoleEnum.USER) && !comment.getUser().getId().equals(user.getId()) ) {
            throw new IllegalArgumentException("작성자만 수정할 수 있습니다.");
        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment);
    }

    // Comment 삭제
    public StatusResponseDto deleteComment(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("해당 댓글이 존재하지 않습니다."));

        if (user.getRole().equals(UserRoleEnum.USER) && !comment.getUser().getId().equals(user.getId()) ) {
            return new StatusResponseDto("작성자만 삭제할 수 있습니다.", HttpStatus.BAD_REQUEST.value());
        }
        commentRepository.delete(comment);
        return new StatusResponseDto("삭제 성공", HttpStatus.OK.value());
    }
}