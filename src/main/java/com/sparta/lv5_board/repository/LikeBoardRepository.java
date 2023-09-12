package com.sparta.lv5_board.repository;

import com.sparta.lv5_board.entity.Board;
import com.sparta.lv5_board.entity.Comment;
import com.sparta.lv5_board.entity.LikeBoard;
import com.sparta.lv5_board.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeBoardRepository extends JpaRepository<LikeBoard, Long> {

    boolean existsByUserAndBoard(User user, Board board);

    void deleteByUserAndBoard(User user, Board board);

    boolean existsByUserAndComment(User user, Comment comment);

    void deleteByUserAndComment(User user, Comment comment);
}
