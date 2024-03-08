package com.sparta.todolistmanage.service;


import com.sparta.todolistmanage.dto.request.TodoRequestDto;
import com.sparta.todolistmanage.dto.request.TodoUpdateRequestDto;
import com.sparta.todolistmanage.dto.response.TodoListResponseDto;
import com.sparta.todolistmanage.dto.response.TodoResponseDto;
import com.sparta.todolistmanage.entity.User;

import java.nio.file.AccessDeniedException;
import java.util.List;

public interface TodoService {

    /**
     *  게시글 생성
     * @param requestDto 게시글 생성 요청 정보
     * @param user 게시글 생성 요청자
     * @return 게시글 생성결과
     */
    TodoResponseDto createTodo(TodoRequestDto requestDto, User user);

    /**
     *  게시글 내용 요청
     * @param todoId 게시글 ID
     * @return 게시글 정보
     */
    List<TodoResponseDto> getTodoById(Long todoId);

    /**
     *  전체 게시글 요청
     * @return 게시글 목록 리스트
     */
    List<TodoListResponseDto> getAllTodo();

    /**
     *  게시글 수정
     * @param todoId 게시글 ID
     * @param requestDto 게시글 수정 요청 정보
     * @param user 게시글 수정 요청자
     * @return 게시글 수정 결과
     */
    TodoResponseDto updateTodo(Long todoId, TodoUpdateRequestDto requestDto, User user) throws Exception;

    /**
     *  게시글 완료처리
     * @param todoId 게시글완료 할 게시글 ID
     * @param user 게시글 완료처리 수정 요청자
     * @return 게시글 완료처리 결과
     */
    TodoResponseDto completeTodo(Long todoId, User user) throws Exception;
}
