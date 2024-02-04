package com.sparta.todolistmanage.entity;

import com.sparta.todolistmanage.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Entity
@Table(name="comment")
@NoArgsConstructor
@Getter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(name ="contents", nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "todo_id", nullable = false)
    private Todo todo;


    public Comment(User user, Todo todo, CommentRequestDto requestDto) {
        this.user = user;
        this.todo = todo;
        this.contents = requestDto.getContent();

    }

    public void update(CommentRequestDto requestDto) {
        this.contents = requestDto.getContent();
    }
}
