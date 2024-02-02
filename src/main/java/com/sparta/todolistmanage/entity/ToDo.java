package com.sparta.todolistmanage.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@Table(name = "todo")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "todo_name", nullable = false)
    private String toDoName;

    @Column(name = "contents", nullable = false, length = 500)
    private String contents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ToDo(String toDoName, String contents, User user) {
        this.toDoName = toDoName;
        this.contents = contents;
        this.user = user;
    }
}
