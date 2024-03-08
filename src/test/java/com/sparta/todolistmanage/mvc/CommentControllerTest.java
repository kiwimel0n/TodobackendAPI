package com.sparta.todolistmanage.mvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.todolistmanage.config.WebSecurityConfig;
import com.sparta.todolistmanage.controller.CommentController;
import com.sparta.todolistmanage.dto.request.CommentRequestDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.CommentService;
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

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CommentController .class,
        excludeFilters = {
                @ComponentScan.Filter(
                    type = FilterType.ASSIGNABLE_TYPE,
                    classes = WebSecurityConfig.class
                )
        })
@ActiveProfiles("test")
class CommentControllerTest {

    private MockMvc mvc;

    private Principal mockPrincipal;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @MockBean
    CommentService commentService;

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
    @DisplayName("댓글 생성api 테스트")
    void test1() throws Exception{
        //given
        this.mockUserSetup();
        Long todoId = 1L;
        Todo todo = new Todo(1L,"안녕하세용","반갑구리~!",false,new User());

        when(todoService.findTodoById(todoId)).thenReturn(todo);

        CommentRequestDto requestDto = new CommentRequestDto("잘보구 있습니다.");
        String commentInfo = objectMapper.writeValueAsString(requestDto);

        // When
        ResultActions result = mvc.perform(post("/api/todo/{todoId}/comment/create", todoId)
                .content(commentInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        );

        //then
        result.andExpect(status().isCreated())
                .andDo(print());
    }


}