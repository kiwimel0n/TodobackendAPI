package com.sparta.todolistmanage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
public class TodoListManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(TodoListManageApplication.class, args);
    }

}
