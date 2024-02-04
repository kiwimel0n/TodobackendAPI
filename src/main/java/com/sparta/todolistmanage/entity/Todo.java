package com.sparta.todolistmanage.entity;

import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoUpdateRequestDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Todo extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long todoId;

    @Column(name = "todo_name", nullable = false)
    private String todoName;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @Column(name = " complete", nullable = false, columnDefinition = "boolean default false")
    private boolean complete;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;





    public Todo(TodoRequestDto requestDto, User user) {
        this.todoName = requestDto.getTodoName();
        this.contents = requestDto.getContents();
        this.user = user;
    }

    public void update(TodoUpdateRequestDto requestDto) {
        this.todoName = requestDto.getTodoName();
        this.contents = requestDto.getContents();
    }

    public void completeTodo(Todo todo){
        this.complete = true;
    }

}
