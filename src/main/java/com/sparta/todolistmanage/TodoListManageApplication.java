package com.sparta.todolistmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class TodoListManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListManageApplication.class, args);
    }

}
