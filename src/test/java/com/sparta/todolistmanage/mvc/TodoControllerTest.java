package com.sparta.todolistmanage.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todolistmanage.config.WebSecurityConfig;
import com.sparta.todolistmanage.controller.TodoController;
import com.sparta.todolistmanage.dto.request.TodoRequestDto;
import com.sparta.todolistmanage.dto.request.TodoUpdateRequestDto;
import com.sparta.todolistmanage.dto.response.TodoListResponseDto;
import com.sparta.todolistmanage.dto.response.TodoResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TodoController.class,
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = WebSecurityConfig.class
                )
        })
@ActiveProfiles("test")
class TodoControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal;


    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockUserSetup() {
        String username = "bob4";
        String password = "aA1234567";
        User testUser = new User(username,password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails,"", testUserDetails.getAuthorities());
    }

    @Test
    @DisplayName("Todo 생성 api Todo 생성")
    void test1()throws Exception{
        //given
        this.mockUserSetup();
        String todoName = "안녕하숑";
        String contents = "반갑구리~!";
        TodoRequestDto requestDto = new TodoRequestDto(todoName,contents);


        String todoInfo = objectMapper.writeValueAsString(requestDto);

        //when-then
        mvc.perform(post("/api/todo/create")
                .content(todoInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        )
                .andExpect(status().isCreated())
                .andDo(print());
    }

    @Test
    @DisplayName("선택한 todo 조회 api")
    void test2() throws Exception{
        //given
        Long todoId = 1L;
        Todo todo = new Todo(1L,"안녕하세용","반갑구리~!",false,new User());
        List<TodoResponseDto> mockResponseDto = List.of(new TodoResponseDto(todo));

        when(todoService.getTodoById(todoId)).thenReturn(mockResponseDto);

        //when
        ResultActions result = mvc.perform(get("/api/todo/search/{id}",todoId));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @DisplayName("todo 전체 목록조회 api")
    void test3() throws Exception{
        //given
        Todo todo = new Todo(1L,"안녕하세용","반갑구리~!",false,new User());
        Todo todo2 = new Todo(2L,"안녕하세용4","반갑구리~!3",false,new User());
        Todo todo3 = new Todo(3L,"안녕하세용5","반갑구리~!4",false,new User());
        List<TodoListResponseDto> mockResponseDto = List.of(
                new TodoListResponseDto(todo),
                new TodoListResponseDto(todo2),
                new TodoListResponseDto(todo3)
        );

        when(todoService.getAllTodo()).thenReturn(mockResponseDto);

        //when
        ResultActions result = mvc.perform(get("/api/todo/search/all"));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    @DisplayName("Todo 작성자가 todo수정 api 테스트")
    void test4() throws Exception{
        //given
        User user = new User("bob3","Aa1234567");
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails,"", testUserDetails.getAuthorities());

        Long todoId = 1L;
        Todo todo = new Todo(1L,"가입인사","반갑습니다,",false,user);
        TodoUpdateRequestDto requestDto = new TodoUpdateRequestDto("안녕하십니까","반가웠습니다.");
        TodoResponseDto todoResponseDto = new TodoResponseDto(todo.getTodoId(),requestDto.getTodoName(),requestDto.getContents(),user.getUsername(), todo.getCreatedAt(),todo.getModifiedAt(),todo.isComplete());

        when(todoService.updateTodo(1L,requestDto,user)).thenReturn(todoResponseDto);

        String todoInfo = objectMapper.writeValueAsString(todoResponseDto);

        //when
        ResultActions result = mvc.perform(put("/api/todo/{id}",todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoInfo)
                .principal(mockPrincipal));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    @DisplayName("todo 작성자 완료 업데이트 테스트")
    void test5() throws Exception{
        //given
        User user = new User("bob3","Aa1234567");
        UserDetailsImpl testUserDetails = new UserDetailsImpl(user);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails,"", testUserDetails.getAuthorities());

        Long todoId = 1L;
        Todo todo = new Todo(1L,"가입인사","반갑습니다,",false,user);
        TodoResponseDto responseDto = new TodoResponseDto(
                todo.getTodoId(),
                todo.getTodoName(),
                todo.getContents(),
                todo.getUser().getUsername(),
                todo.getCreatedAt(),
                todo.getModifiedAt(),
                true
        );

       when(todoService.completeTodo(todoId, user)).thenReturn(responseDto);

        ResultActions result = mvc.perform(patch("/api/todo/complete/{id}", todoId)
                .contentType(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal));

        result.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

        verify(todoService).completeTodo(todoId, user);

    }










}