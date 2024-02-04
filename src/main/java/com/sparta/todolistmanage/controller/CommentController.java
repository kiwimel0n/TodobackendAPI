package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.CommentRequestDto;
import com.sparta.todolistmanage.dto.CommentResponseDto;
import com.sparta.todolistmanage.entity.Comment;
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
@RequestMapping("/api/todo/")
@RequiredArgsConstructor
public class CommentController {

    private final TodoService todoService;

    private final CommentService commentService;

    @PostMapping("{todoId}/comment/create")
    public ResponseEntity<?> createComment(
            @PathVariable Long todoId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){

            Todo todo = todoService.findTodoById(todoId);

            CommentResponseDto responseDto = commentService.createComment(userDetails.getUser(),todo,requestDto);

            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @PutMapping("{todoId}/comment/update/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

//    @DeleteMapping("/delete/{commentId}")
//    public ResponseEntity<?> deleteComment(
//            @PathVariable Long todoId,
//            @PathVariable Long commentId,
//            @AuthenticationPrincipal UserDetailsImpl userDetails
//    ){
//
//    }
}
