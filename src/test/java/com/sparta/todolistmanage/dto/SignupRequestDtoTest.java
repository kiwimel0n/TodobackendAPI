package com.sparta.todolistmanage.dto;

import jakarta.validation.ValidationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SignupRequestDtoTest {

    @Test
    @DisplayName("정상적인 회원가입 패턴입력")
    void validUsernameAndPassword(){
        //given
        String username = "bob4";
        String password = "aA1234567";


        assertDoesNotThrow(()->{
           SignupRequestDto signupRequestDto = new SignupRequestDto(username, password);
        });


    }

    @Test
    @DisplayName("비정상적인 회원가입 username 패턴입력")
    void invalidUsername(){
        //given
        String username = "Bob4";
        String password = "aA1234567";


       Throwable exception = assertThrows(ValidationException.class,() ->{
            new SignupRequestDto(username, password);
        });

       assertEquals("잘못된 username 또는 password 입력입니다", exception.getMessage());


    }

    @Test
    @DisplayName("비정상적인 회원가입 username 패턴입력")
    void invalidPassword(){
        //given
        String username = "bob4";
        String password = "aA1234567@";


        Throwable exception = assertThrows(ValidationException.class,() ->{
            new SignupRequestDto(username, password);
        });

        assertEquals("잘못된 username 또는 password 입력입니다", exception.getMessage());


    }

}