package com.sparta.todolistmanage.repository;

import com.sparta.todolistmanage.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoRepository extends JpaRepository<Todo, Long> {
}
