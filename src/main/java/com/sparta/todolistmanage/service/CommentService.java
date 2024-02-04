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

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user){
        Comment comment = findCommentById(commentId);

        if(!comment.getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("댓글 수정은 해당 사용자가 작성한 댓글만 수정가능합니다.");
        }
        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public Comment findCommentById(Long commentId) {

        return commentRepository.findById(commentId).orElseThrow(
                 (()-> new IllegalArgumentException("해당 Id의 댓글이 존재하지 않습니다."))
         );

    }

    @Transactional
    public void deleteComment(Long commentId, User user) {
        Comment comment = findCommentById(commentId);
        if(!comment.getUser().getUsername().equals(user.getUsername())){
            throw new IllegalArgumentException("댓글의 삭제는 작성자만이 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
