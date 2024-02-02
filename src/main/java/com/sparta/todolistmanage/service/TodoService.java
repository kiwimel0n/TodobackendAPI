package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.dto.TodoUpdateRequestDto;
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

    @Transactional
    public TodoResponseDto updateTodo(Long id, TodoUpdateRequestDto requestDto, User user) {
        Todo todo = toDoRepository.findById(id).orElseThrow(
                ()-> new IllegalArgumentException("해당 Id를 가진 Todo를 찾을 수 없습니다.")
        );

        if(user.getUsername().equals(todo.getUser().getUsername())) {
            todo.update(requestDto);
            return new TodoResponseDto(todo);
        }
        throw new IllegalArgumentException("해당 Todo은 작성자만 수정할 수 있습니다.");
    }
}