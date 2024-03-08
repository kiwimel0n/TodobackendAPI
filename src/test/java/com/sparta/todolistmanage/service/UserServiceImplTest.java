package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.SignupRequestDto;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class UserServiceImplTest {

    @Mock
    UserRepository userRepository;
    @Spy
    PasswordEncoder passwordEncoder;
    @InjectMocks
    UserServiceImpl userServiceImpl;




    @Test
    @DisplayName("정상적인 회원가입")
    void test1() {
        //given
        String username = "bob4";
        String password = "aA12345678";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        //when
        SignupRequestDto signupRequestDto = new SignupRequestDto(username, password);
        userServiceImpl.signup(signupRequestDto);

        //then
        verify(userRepository, times(1)).save(any(User.class));
        verify(passwordEncoder, times(1)).encode(any(String.class));

    }

    @Test
    @DisplayName("중복된 username 회원가입")
    void test2() {
        //given
       String username = "bob5";
       String password = "aA12345678";

       when(userRepository.findByUsername(username)).thenReturn(Optional.of(new User()));
        //when
       Throwable exception = assertThrows(IllegalArgumentException.class, ()->{
            SignupRequestDto signupRequestDto = new SignupRequestDto(username, password);
            userServiceImpl.signup(signupRequestDto);
        });


       //then
        assertEquals("중복된 사용자가 존재 합니다.",exception.getMessage());




    }


}