package com.sparta.todolistmanage.repository;

import com.sparta.todolistmanage.entity.Todo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TodoRepositoryQuery {
    List<Todo> findAllTodo(Pageable pageable);

}
