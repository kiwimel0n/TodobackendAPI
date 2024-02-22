package com.sparta.todolistmanage.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableJpaAuditing
@EnableWebSecurity
public class JpaConfig {
}