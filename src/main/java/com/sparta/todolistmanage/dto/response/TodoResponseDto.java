package com.sparta.todolistmanage.dto.response;

import com.sparta.todolistmanage.entity.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class TodoResponseDto {

    private Long todoId;

    private String todoName;

    private String contents;

    private String username;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private boolean complete;

    public TodoResponseDto(Todo todo) {
        this.todoId = todo.getTodoId();
        this.todoName = todo.getTodoName();
        this.contents = todo.getContents();
        this.createdAt = todo.getCreatedAt();
        this.modifiedAt = todo.getModifiedAt();
        this.username = todo.getUser().getUsername();
        this.complete = todo.isComplete();

    }

}
