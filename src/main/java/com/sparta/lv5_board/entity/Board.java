package com.sparta.lv5_board.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sparta.lv5_board.dto.BoardRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "board")
@NoArgsConstructor
public class Board extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;//제목

    @Column(name = "username", nullable = false)
    private String username;//작성자명

    @Column(name = "contents", nullable = false)
    private String contents;//작성 내용

    @Column(name = "password", nullable = false)
    private String password;//비밀번호

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "like_cnt")
    private Integer likecnt = 0;


    public Board(BoardRequestDto requestDto, User user) {
        this.title = requestDto.getTitle();
        this.username = user.getUsername();
        this.contents = requestDto.getContents();
        this.password = user.getPassword();
        this.user = user;
    }

    public void update(BoardRequestDto requestDto) {
        this.title = requestDto.getTitle();
        this.username = user.getUsername();
        this.contents = requestDto.getContents();
    }
}
