package com.sparta.todolistmanage.dto;

import com.sparta.todolistmanage.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {

    private Long commentId;

    private Long todoId;

    private String userName;

    private String content;

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getCommentId();
        this.todoId = comment.getTodo().getTodoId();
        this.userName = comment.getUser().getUsername();
        this.content = comment.getContents();
    }


}
