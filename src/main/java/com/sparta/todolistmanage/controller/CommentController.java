package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.CommentRequestDto;
import com.sparta.todolistmanage.dto.CommentResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.CommentService;
import com.sparta.todolistmanage.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(value="/api/todo/{todoId}/comment")
@RequiredArgsConstructor
public class CommentController {

    private final TodoService todoService;

    private final CommentService commentService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createComment(
            @PathVariable Long todoId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

            Todo todo = todoService.findTodoById(todoId);

            CommentResponseDto responseDto = commentService.createComment(userDetails.getUser(),todo,requestDto);

            ResponseMessage message = new ResponseMessage();
            HttpHeaders headers= new HttpHeaders();
            headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

            message.setStatus(StatusEnum.CREATED);
            message.setMessage("댓글 생성 성공");
            message.setData(requestDto);

            return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseMessage> updateComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());

        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));

        message.setStatus(StatusEnum.OK);
        message.setMessage("댓글 수정 성공");
        message.setData(requestDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseMessage> deleteComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        commentService.deleteComment(commentId, userDetails.getUser());

        ResponseMessage message = new ResponseMessage();


        message.setStatus(StatusEnum.OK);
        message.setMessage("댓글 삭제 성공");


        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
