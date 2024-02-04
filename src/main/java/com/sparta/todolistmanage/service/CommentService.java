package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.CommentRequestDto;
import com.sparta.todolistmanage.dto.CommentResponseDto;
import com.sparta.todolistmanage.entity.Comment;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    @Transactional
    public CommentResponseDto createComment(User user, Todo todo, CommentRequestDto requestDto) {
        Comment comment = commentRepository.save(new Comment(user,todo,requestDto));

        return new CommentResponseDto(comment);

    }
}
