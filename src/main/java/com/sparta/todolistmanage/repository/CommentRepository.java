package com.sparta.todolistmanage.repository;

import com.sparta.todolistmanage.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment,Long> {

}
