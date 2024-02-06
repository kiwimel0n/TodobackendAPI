package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.TodoListResponseDto;
import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.dto.TodoUpdateRequestDto;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;


    @PostMapping("/create")
    public ResponseEntity<ResponseMessage> createTodo(
            @RequestBody TodoRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userDetails.getUser());

        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        message.setStatus(StatusEnum.CREATED);
        message.setMessage("Todo 생성 성공");
        message.setData(responseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.CREATED);
    }

    @GetMapping("/search/{id}")
    public ResponseEntity<ResponseMessage> getTodoById(@PathVariable Long id){

        List<TodoResponseDto> responseDto = todoService.getTodoById(id);

        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        message.setStatus(StatusEnum.OK);
        message.setData(responseDto);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @GetMapping("/search/all")
    public ResponseEntity<ResponseMessage> getTodoList(){

        List<TodoListResponseDto> responseDtoList = todoService.getAllTodo();

        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        message.setStatus(StatusEnum.OK);
        message.setData(responseDtoList);

        return new ResponseEntity<>(message, headers, HttpStatus.OK);

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseMessage> updateTodo(
            @PathVariable Long id,
            @RequestBody TodoUpdateRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

       TodoResponseDto responseDto = todoService.updateTodo(id, requestDto, userDetails.getUser());

        ResponseMessage message = new ResponseMessage();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(new MediaType("application", "json", StandardCharsets.UTF_8));
        message.setStatus(StatusEnum.OK);
        message.setMessage("Todo 수정 성공");
        message.setData(responseDto);

       return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PatchMapping("/complete/{id}")
    public ResponseEntity<ResponseMessage> completeTodo(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetailsImpl userDetails){

       TodoResponseDto responseDto = todoService.completeTodo(id, userDetails.getUser());

        ResponseMessage message = new ResponseMessage();
        message.setStatus(StatusEnum.OK);
        message.setData(responseDto);

       return new ResponseEntity<>(message, HttpStatus.OK);
    }

}
