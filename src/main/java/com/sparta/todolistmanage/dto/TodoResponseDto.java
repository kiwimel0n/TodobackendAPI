package com.sparta.todolistmanage.dto;

import com.sparta.todolistmanage.entity.Todo;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TodoResponseDto {

    private Long id;

    private String todoName;

    private String contents;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.todoName = todo.getTodoName();
        this.contents = todo.getContents();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.username = todo.getUser().getUsername();

    }
}
