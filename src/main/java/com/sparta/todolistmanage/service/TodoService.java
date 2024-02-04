package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.TodoListResponseDto;
import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.dto.TodoUpdateRequestDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public List<TodoResponseDto> getTodoById(Long todoId) {
        return toDoRepository.findById(todoId)
                .stream()
                .map(TodoResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<TodoListResponseDto> getAllTodo() {
        return toDoRepository.findAll()
                .stream()
                .map(TodoListResponseDto::new)
                .sorted(Comparator.comparing(TodoListResponseDto::getModifiedAt).reversed())
                .toList();
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoUpdateRequestDto requestDto, User user) {
        Todo todo = findTodoById(todoId);

        if(!user.getUsername().equals(todo.getUser().getUsername())) {
            throw new IllegalArgumentException("해당 Todo은 작성자만 수정할 수 있습니다.");
        }
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto completeTodo(Long todoId, User user) {
        Todo todo = findTodoById(todoId);

        if(!user.getUsername().equals(todo.getUser().getUsername())) {
            throw new IllegalArgumentException("해당 Todo는 작성자만 완료처리가 가능합니다");
        }
        todo.completeTodo(todo);
        return new TodoResponseDto(todo);
    }

    public Todo findTodoById(Long todoId) {
        return toDoRepository.findById(todoId).orElseThrow(
                ()-> new IllegalArgumentException("해당하는 Id를 가진 Todo를 찾을 수 없습니다.")
        );
    }


}
