package com.sparta.lv5_board.repository;

import com.sparta.lv5_board.entity.Board;
import com.sparta.lv5_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByModifiedAtDesc();

    List<Board> findAllByUserOrderByModifiedAtDesc(User user);
}
