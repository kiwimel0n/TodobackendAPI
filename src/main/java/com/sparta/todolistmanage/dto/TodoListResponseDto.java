package com.sparta.todolistmanage.dto;

import com.sparta.todolistmanage.entity.Todo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
public class TodoListResponseDto {

    private Long id;

    private String todoName;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private boolean complete;



    public TodoListResponseDto(Todo todo){
        this.id = todo.getId();
        this.todoName = todo.getTodoName();
        this.username =todo.getUser().getUsername();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.complete = todo.isComplete();
    }


}
