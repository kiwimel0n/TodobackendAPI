package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final ToDoRepository toDoRepository;

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user){
        Todo todo = toDoRepository.save(new Todo(requestDto,  user));
        return new TodoResponseDto(todo);
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> getTodoById(Long toDoId) {
        return toDoRepository.findById(toDoId)
                .stream()
                .map(TodoResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TodoResponseDto> getAllTodo() {
        return toDoRepository.findAll()
                .stream()
                .map(TodoResponseDto::new)
                .sorted(Comparator.comparing(TodoResponseDto::getModifiedAt).reversed())
                .toList();
    }
}
