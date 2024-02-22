package com.sparta.todolistmanage.security;

import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @DisplayName("유저정보 불러오기 - 유저가 존재할시")
    void test1() {
        // Given
        String username = "bob4";
        User user = new User(username, "aA1234567");

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        // When
        UserDetails result = userDetailsService.loadUserByUsername(username);

        //then
        verify(userRepository, times(1)).findByUsername(username);
        assertEquals(user.getUsername(),result.getUsername());
    }

    @Test
    @DisplayName("유저정보 불러오기 - 유저가 존재하지않을때")
    void test2() {
        // Given
        String username = "bob5";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // When
        Throwable exception = assertThrows(UsernameNotFoundException.class, () ->
                userDetailsService.loadUserByUsername(username)
        );

        assertEquals("Not Found " + username,exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }
}