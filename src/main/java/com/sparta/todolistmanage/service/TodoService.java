package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.TodoRequestDto;
import com.sparta.todolistmanage.dto.request.TodoUpdateRequestDto;
import com.sparta.todolistmanage.dto.response.TodoListResponseDto;
import com.sparta.todolistmanage.dto.response.TodoResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final ToDoRepository toDoRepository;

    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user){
        Todo todo = new Todo(requestDto,  user);

        toDoRepository.save(todo);
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
                .sorted(Comparator.comparing(TodoListResponseDto::getModifiedAt, Comparator.nullsLast(Comparator.reverseOrder())))
                .toList();
    }

    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoUpdateRequestDto requestDto, User user) throws Exception {
        Todo todo = findTodoById(todoId);

        if(!user.getUsername().equals(todo.getUser().getUsername())) {
            throw new AccessDeniedException("해당 Todo은 작성자만 수정할 수 있습니다.");
        }
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Transactional
    public TodoResponseDto completeTodo(Long todoId, User user) throws Exception {
        Todo todo = findTodoById(todoId);

        if(!user.getUsername().equals(todo.getUser().getUsername())) {
            throw new AccessDeniedException("해당 Todo는 작성자만 완료처리가 가능합니다");
        }
        todo.completeTodo();
        return new TodoResponseDto(todo);
    }

    public Todo findTodoById(Long todoId) {
        return toDoRepository.findById(todoId).orElseThrow(
                ()-> new NoSuchElementException("해당하는 Id를 가진 Todo를 찾을 수 없습니다.")
        );
    }


}
