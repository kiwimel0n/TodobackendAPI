package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.CommentRequestDto;
import com.sparta.todolistmanage.dto.response.CommentResponseDto;
import com.sparta.todolistmanage.entity.Comment;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.CommentRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class CommentServiceTest {

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    CommentService commentService;

    @Test
    @DisplayName("게시글 댓글 작성")
    void test1() {
        //given
        User user = new User("bob3","Aa1234567");
        Todo todo = new Todo(1L,"가입인사","반갑습니다,",false,new User());
        String contents = "잘 보고 있습니다.";
        CommentRequestDto commentRequestDto = new CommentRequestDto(contents);

        //when
        CommentResponseDto commentResponseDto = commentService.createComment(user,todo,commentRequestDto);

        //then
        assertEquals(user.getUsername(),commentResponseDto.getUserName());
        assertEquals(commentRequestDto.getContents(),commentResponseDto.getContent());
        assertEquals(todo.getTodoId(),commentResponseDto.getTodoId());

    }

    @Test
    @DisplayName("댓글id에 해당하는 댓글을 찾았을 때")
    void test2() {
        //given
        Long commentId = 1L;
        Comment comment = new Comment(1L, "잘보구 있어요.", new User(), new Todo());

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        //when
        Comment result = commentService.findCommentById(commentId);

        //then
        assertEquals(comment.getCommentId(),result.getCommentId());
    }

    @Test
    @DisplayName("댓글id에 해당하는 댓글을 찾지 못했을 때")
    void test3(){
        //given
        Long commentId = 2L;

        when(commentRepository.findById(commentId)).thenReturn(Optional.empty());

        //when
        Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            commentService.findCommentById(commentId);
        });

        //then
        assertEquals("해당 Id의 댓글이 존재하지 않습니다.", exception.getMessage());
    }


    @Test
    @DisplayName("댓글 작성자가 댓글 수정")
    void test4() throws Exception {
        //given
        User user = new User("bob3","Aa1234567");
        Long commentId = 1L;
        Comment comment = new Comment(1L, "잘보구 있어요.", user, new Todo());
        CommentRequestDto commentRequestDto = new CommentRequestDto("나중에 저랑같이 뭐하러 가시죠");

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        //when
        CommentResponseDto result = commentService.updateComment(1L,commentRequestDto,user);

        //then
        assertEquals(commentRequestDto.getContents(),result.getContent());
        assertEquals(comment.getCommentId(),result.getCommentId());
        assertEquals(comment.getUser().getUsername(),result.getUserName());

    }

    @Test
    @DisplayName("댓글 작성자가 댓글 삭제")
    void test5() throws Exception {
        //given
        User user = new User("bob3","Aa1234567");
        Long commentId = 3L;
        Comment comment = new Comment(3L, "그러셨나요 저는 다르네요.", user, new Todo());

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(comment));

        //when
        commentService.deleteComment(commentId,user);

        //then
        verify(commentRepository, times(1)).delete(any(Comment.class));
    }





}