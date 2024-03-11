package com.sparta.todolistmanage.repository;

import com.sparta.todolistmanage.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> , TodoRepositoryQuery{

}
