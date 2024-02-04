package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.LoginRequestDto;
import com.sparta.todolistmanage.dto.SignupRequestDto;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String encodedPassword = passwordEncoder.encode(requestDto.getPassword());

        //회원 중복 확인
        Optional<User> checkUsername =userRepository.findByUsername(username);
        if(checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재 합니다.");
        }

        User user = new User(username,encodedPassword);
        userRepository.save(user);

    }

}
