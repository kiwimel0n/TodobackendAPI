package com.sparta.todolistmanage.repository;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.todolistmanage.entity.QTodo;
import com.sparta.todolistmanage.entity.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.sparta.todolistmanage.entity.QTodo.todo;

@Repository
@RequiredArgsConstructor
public class TodoRepositoryQueryImpl implements TodoRepositoryQuery{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Todo> findAllTodo(Pageable pageable){
        QTodo qTodo = todo;

        OrderSpecifier<?> orderSpecifier = new OrderSpecifier<>(Order.DESC, todo.modifiedAt);

       return jpaQueryFactory
               .select(qTodo)
               .from(qTodo)
               //.orderBy(qTodo.modifiedAt.desc().nullsLast())
               .orderBy(orderSpecifier)
               .offset(pageable.getOffset())
               .limit(pageable.getPageSize())
               .fetch();

    }

}
