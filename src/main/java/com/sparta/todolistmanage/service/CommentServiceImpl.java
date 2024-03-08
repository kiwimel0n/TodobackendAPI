package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.CommentRequestDto;
import com.sparta.todolistmanage.dto.response.CommentResponseDto;
import com.sparta.todolistmanage.entity.Comment;
import com.sparta.todolistmanage.entity.Todo;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.AccessDeniedException;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;

    @Override
    @Transactional
    public CommentResponseDto createComment(User user, Todo todo, CommentRequestDto requestDto) {
        Comment comment = new Comment(user,todo,requestDto);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Override
    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto requestDto, User user) throws Exception {
        Comment comment = findCommentById(commentId);

        if(!comment.getUser().getUsername().equals(user.getUsername())){
            throw new AccessDeniedException("댓글 수정은 해당 사용자가 작성한 댓글만 수정가능합니다.");
        }
        comment.update(requestDto);

        return new CommentResponseDto(comment);
    }

    public Comment findCommentById(Long commentId) {

        return commentRepository.findById(commentId).orElseThrow(
                 (()-> new NoSuchElementException("해당 Id의 댓글이 존재하지 않습니다."))
         );

    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, User user) throws Exception {
        Comment comment = findCommentById(commentId);
        if(!comment.getUser().getUsername().equals(user.getUsername())){
            throw new AccessDeniedException("댓글의 삭제는 작성자만이 삭제할 수 있습니다.");
        }
        commentRepository.delete(comment);
    }
}
