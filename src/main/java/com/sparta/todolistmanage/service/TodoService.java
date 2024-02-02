package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final ToDoRepository toDoRepository;

    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user){
        Todo todo = toDoRepository.save(new Todo(requestDto,  user));
        return new TodoResponseDto(todo);
    }
}
