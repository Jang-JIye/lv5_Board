package com.sparta.lv5_board.repository;

import com.sparta.lv5_board.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void delete(Comment comment);
}
