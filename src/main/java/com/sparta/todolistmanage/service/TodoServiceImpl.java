package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.TodoRequestDto;
import com.sparta.todolistmanage.dto.request.TodoUpdateRequestDto;
import com.sparta.todolistmanage.dto.response.TodoListResponseDto;
import com.sparta.todolistmanage.dto.response.TodoResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.exception.TodoNotFoundException;
import com.sparta.todolistmanage.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService{

    private final TodoRepository todoRepository;

    @Override
    @Transactional
    public TodoResponseDto createTodo(TodoRequestDto requestDto, User user){
        Todo todo = new Todo(requestDto,  user);

        todoRepository.save(todo);
        return new TodoResponseDto(todo);
    }


    @Override
    @Transactional(readOnly = true)
    public List<TodoResponseDto> getTodoById(Long todoId) {
        return todoRepository.findById(todoId)
                .stream()
                .map(TodoResponseDto::new)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<TodoListResponseDto> getAllTodo(Pageable pageable) {
        return todoRepository.findAllTodo(pageable)
                .stream()
                .map(TodoListResponseDto::new)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TodoResponseDto updateTodo(Long todoId, TodoUpdateRequestDto requestDto, User user) throws Exception {
        Todo todo = findTodoById(todoId);

        if(!user.getUsername().equals(todo.getUser().getUsername())) {
            throw new AccessDeniedException("해당 Todo은 작성자만 수정할 수 있습니다.");
        }
        todo.update(requestDto);
        return new TodoResponseDto(todo);
    }

    @Override
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
        return todoRepository.findById(todoId).orElseThrow(
                ()-> new TodoNotFoundException("해당하는 Id를 가진 Todo를 찾을 수 없습니다.")
        );
    }


}
