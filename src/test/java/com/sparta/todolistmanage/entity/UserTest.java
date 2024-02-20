package com.sparta.todolistmanage.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class UserTest {

    User user;

    @BeforeEach
    void setUp() {
        user = new User("Bob4", "12345678@");
    }

    @Test
    @DisplayName("User 객체 초기화 테스트")
    void userInitTest() {
        assertNotNull(user);
        assertEquals("Bob4",user.getUsername());
        assertEquals("12345678@",user.getPassword());

    }
}