package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.TodoListResponseDto;
import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.dto.TodoUpdateRequestDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.ToDoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class TodoServiceTest {

    @Mock
    ToDoRepository todoRepository;

    @InjectMocks
    TodoService todoService;

    @Test
    @DisplayName("정상적인 todo 생성")
    void test1() {
        //given
        String todoName = "가입인사";
        String contents = "안녕하세요.";

        TodoRequestDto todoRequestDto = new TodoRequestDto(todoName, contents);

        User user = new User();

        //when
        TodoResponseDto todoResponseDto = todoService.createTodo(todoRequestDto, user);

        //then
        verify(todoRepository, times(1)).save(any(Todo.class));
        assertEquals(todoName, todoResponseDto.getTodoName());
        assertEquals(contents, todoResponseDto.getContents());
    }


    @Test
    @DisplayName("id에 해당하는 todo가 존재할 때 조회")
    void test2() {
        //given
        Long todoId = 1L;
        Todo todo = new Todo(todoId, "가입인사", "안녕하세요", false, new User());
        List<TodoResponseDto> data = List.of(
                new TodoResponseDto(todo)
        );

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        //when
        List<TodoResponseDto> result = todoService.getTodoById(1L);

        //then
        assertEquals(data.get(0).getTodoId(), result.get(0).getTodoId());
        assertEquals(data.get(0).getTodoName(), result.get(0).getTodoName());
        assertEquals(data.get(0).getContents(), result.get(0).getContents());
        assertEquals(data.get(0).getUsername(), result.get(0).getUsername());
    }

    @Test
    @DisplayName("전체 todo를 list로 조회")
    void test3() {
        //given
        Todo todo1 = new Todo(1L, "가입인사", "안녕하세요", false, new User());
        Todo todo2 = new Todo(2L, "가입인사2", "안녕하세요3", false, new User());
        Todo todo3 = new Todo(3L, "가입인사4", "안녕하세요5", false, new User());
        List<Todo> dataList = List.of(
                todo1,
                todo2,
                todo3
        );

        when(todoRepository.findAll()).thenReturn(dataList);

        //when
        List<TodoListResponseDto> result= todoService.getAllTodo();

        //then
        assertEquals(3, result.size());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("todoid에 해당하는 등록된 todo찾았을 때")
    void test4() {
        //given
        Long todoId = 1L;
        Todo todo = new Todo(1L,"가입인사","안녕하세요",false,new User());

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        //when
        Todo result = todoService.findTodoById(todoId);

        //then
        assertEquals(todo.getTodoId(),result.getTodoId());
    }


    @Test
    @DisplayName("todoid에 해당하는 등록된 todo찾을 수 없을 때")
    void test5() {
        //given
        Long todoId = 2L;

        when(todoRepository.findById(todoId)).thenReturn(Optional.empty());


        //when
       Throwable exception = assertThrows(IllegalArgumentException.class,()-> {
           todoService.findTodoById(todoId);
       });

        //then
        assertEquals("해당하는 Id를 가진 Todo를 찾을 수 없습니다.",exception.getMessage());

    }

    @Test
    @DisplayName("정상적으로 등록된 todo에 해당하는 유저가 수정")
    void test6(){
        //given
        User user = new User("bob3","Aa1234567");
        Long todoId = 1L;
        Todo todo = new Todo(1L,"가입인사","반갑습니다,",false,user);
        TodoUpdateRequestDto todoUpdateRequestDto = new TodoUpdateRequestDto("가입인사 2","안녕하십니까!2");

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        //when
        TodoResponseDto result = todoService.updateTodo(todoId,todoUpdateRequestDto,user);

        //then
        assertEquals(todoUpdateRequestDto.getTodoName(),result.getTodoName());
        assertEquals(todoUpdateRequestDto.getContents(),result.getContents());

    }

    @Test
    @DisplayName("todo 작성자의 todoComplete 완료 업데이트")
    void test7(){
        //given
        Long todoId = 1L;
        User user = new User("bob3","Aa1234567");
        Todo todo = new Todo(1L,"가입인사","반갑습니다,",false,user);

        when(todoRepository.findById(todoId)).thenReturn(Optional.of(todo));

        //when
        TodoResponseDto result = todoService.completeTodo(todoId,user);

        //then
        assertTrue(todo.isComplete());


    }


}