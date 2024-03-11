package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.request.PageDTO;
import com.sparta.todolistmanage.dto.request.TodoRequestDto;
import com.sparta.todolistmanage.dto.request.TodoUpdateRequestDto;
import com.sparta.todolistmanage.dto.response.ResponseMessage;
import com.sparta.todolistmanage.dto.response.TodoListResponseDto;
import com.sparta.todolistmanage.dto.response.TodoResponseDto;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.TodoServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoServiceImpl todoServiceImpl;


    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createTodo(
            @RequestBody TodoRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TodoResponseDto responseDto = todoServiceImpl.createTodo(requestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("게시글 생성 성공")
                        .data(responseDto)
                        .build());

    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ResponseMessage> getTodoById(@PathVariable Long id){

        List<TodoResponseDto> responseDto = todoServiceImpl.getTodoById(id);

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .data(responseDto)
                        .build());

    }

    @GetMapping("/search/all")
    public ResponseEntity<ResponseMessage> getTodoList(PageDTO pageDTO){

        List<TodoListResponseDto> responseDtoList = todoServiceImpl.getAllTodo(pageDTO.toPageable());

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .data(responseDtoList)
                        .build());

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

       TodoResponseDto responseDto = todoServiceImpl.updateTodo(id, requestDto, userDetails.getUser());

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("게시글 수정 성공")
                        .data(responseDto)
                        .build());

    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<ResponseMessage> completeTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails) throws Exception {

       TodoResponseDto responseDto = todoServiceImpl.completeTodo(id, userDetails.getUser());

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .data(responseDto)
                        .build());

    }

}
