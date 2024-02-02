package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.TodoRequestDto;
import com.sparta.todolistmanage.dto.TodoResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.security.UserDetailsImpl;
import com.sparta.todolistmanage.service.TodoService;
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

    private final TodoService todoService;


    @PostMapping("/create")
    public ResponseEntity<?> createTodo(@RequestBody TodoRequestDto requestDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails){
        TodoResponseDto responseDto = todoService.createTodo(requestDto, userDetails.getUser());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable Long id){

        List<TodoResponseDto> responseDto = todoService.getTodoById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getTodoList(){

        List<TodoResponseDto> responseDtoList = todoService.getAllTodo();

        return ResponseEntity.status(HttpStatus.OK).body(responseDtoList);

    }

//    @PostMapping("/update/{id}")
//    public ResponseEntity<?> updateTodo(@PathVariable Long id, @RequestBody Todo)
}
