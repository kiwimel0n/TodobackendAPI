package com.sparta.todolistmanage.dto.response;

import com.sparta.todolistmanage.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoListResponseDto {

    private Long todoId;

    private String todoName;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private boolean complete;



    public TodoListResponseDto(Todo todo){
        this.todoId = todo.getTodoId();
        this.todoName = todo.getTodoName();
        this.username =todo.getUser().getUsername();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.complete = todo.isComplete();
    }


}
