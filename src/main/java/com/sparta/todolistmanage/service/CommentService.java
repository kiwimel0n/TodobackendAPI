package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.CommentRequestDto;
import com.sparta.todolistmanage.dto.response.CommentResponseDto;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;

public interface CommentService {

    /**
     * 댓글 생성
     * @param user 댓글생성 요청자
     * @param todo 댓글이 생성될 게시글
     * @param requestDto 댓글 생성 요청정보
     * @return 댓글생성 결과
     */
    CommentResponseDto createComment(User user, Todo todo, CommentRequestDto requestDto);

    /**
     * 댓글 수정
     * @param commentId 수정할 댓글 ID
     * @param requestDto 댓글 수정 요청 정보
     * @param user 댓글 수정 요청자
     * @return 댓글 수정 결과
     */
    CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) throws Exception;

    /**
     * 댓글 삭제
     * @param commentId 삭제할 댓글 ID
     * @param user 댓글 삭제 요청자
     */
    void deleteComment(Long commentId, User user) throws Exception;
}
