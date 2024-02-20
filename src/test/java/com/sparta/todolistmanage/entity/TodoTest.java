package com.sparta.todolistmanage.entity;

import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    User user;

    Todo todo;

    @BeforeEach
    void setUp(){
        user = new User("Bob","12345678@");
        TodoRequestDto todoRequestDto = new TodoRequestDto("가입인사", "안녕하세요");
        todo = new Todo(todoRequestDto,user);
    }

    @Test
    @DisplayName("todo 객체 초기화 테스트")
    void todoInitTest(){
        assertNotNull(todo);
        assertEquals("가입인사", todo.getTodoName());
        assertEquals("안녕하세요", todo.getContents());
        assertFalse(todo.isComplete());
        assertEquals(user, todo.getUser());
    }

    @Test
    @DisplayName("todo 업데이트 테스트")
    void updateTodoTest(){
        TodoUpdateRequestDto todoUpdateRequestDto = new TodoUpdateRequestDto("가입한지 100일", "가입한지 100일이 넘어가네요");

        todo.update(todoUpdateRequestDto);

        assertEquals("가입한지 100일", todo.getTodoName());
        assertEquals("가입한지 100일이 넘어가네요", todo.getContents());

    }

    @Test
    @DisplayName("todo 완료 업데이트 테스트")
    void updateCompleteTodoTest(){
        assertFalse(todo.isComplete());

        todo.completeTodo();

        assertTrue(todo.isComplete());
    }

}