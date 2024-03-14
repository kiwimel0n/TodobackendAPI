package com.sparta.todolistmanage.repository;

import com.sparta.todolistmanage.entity.Token;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {
    Token findByUsername(String username);
}
