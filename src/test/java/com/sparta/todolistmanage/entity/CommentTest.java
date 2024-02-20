package com.sparta.todolistmanage.entity;

import com.sparta.todolistmanage.dto.CommentRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentTest {

    User user;
    Todo todo;
    Comment comment;

    @BeforeEach
    void setUp() {
       user = new User();
       todo = new Todo();
       CommentRequestDto commentRequestDto = new CommentRequestDto("잘 보았습니다.");
       comment = new Comment(user,todo,commentRequestDto);
    }

    @Test
    @DisplayName("comment 객체 초기화 테스트")
    void commentInitTest() {
        assertEquals(user, comment.getUser());
        assertEquals(todo, comment.getTodo());
        assertEquals("잘 보았습니다.", comment.getContents());
    }

    @Test
    @DisplayName("comment 업데이트 테스트")
    void updateComment() {
        CommentRequestDto commentRequestDto = new CommentRequestDto("저는 이러했습니다.");

        comment.update(commentRequestDto);

        assertEquals("저는 이러했습니다.", comment.getContents());
    }


}