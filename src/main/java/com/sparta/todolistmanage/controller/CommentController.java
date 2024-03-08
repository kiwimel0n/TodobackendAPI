package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.request.CommentRequestDto;
import com.sparta.todolistmanage.dto.response.CommentResponseDto;
import com.sparta.todolistmanage.dto.response.ResponseMessage;
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


            return ResponseEntity.ok()
                    .body(ResponseMessage.builder()
                            .httpStatus(HttpStatus.OK.value())
                            .message("댓글 생성 성공")
                            .data(responseDto)
                            .build());
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ResponseMessage> updateComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws Exception {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("댓글 수정 성공")
                        .data(responseDto)
                        .build());

    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ResponseMessage> deleteComment(
            @PathVariable Long todoId,
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) throws Exception {
        commentService.deleteComment(commentId, userDetails.getUser());

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("댓글 삭제 성공")
                        .build());

    }
}
