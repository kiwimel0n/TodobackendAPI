package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.CommentRequestDto;
import com.sparta.todolistmanage.dto.CommentResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.CommentService;
import com.sparta.todolistmanage.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/todo/comment")
@RequiredArgsConstructor
public class CommentController {

    private final TodoService todoService;

    private final CommentService commentService;

    @PostMapping("/create/{todoId}")
    public ResponseEntity<?> createComment(@PathVariable Long todoId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){

            Todo todo = todoService.findTodo(todoId);

            CommentResponseDto responseDto = commentService.createComment(userDetails.getUser(),todo,requestDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }
}
