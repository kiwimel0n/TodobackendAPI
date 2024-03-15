package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.LoginRequestDto;
import com.sparta.todolistmanage.dto.request.SignupRequestDto;
import com.sparta.todolistmanage.dto.response.TokenDto;
import com.sparta.todolistmanage.entity.User;
import com.sparta.todolistmanage.jwt.JwtUtil;
import com.sparta.todolistmanage.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.security.auth.login.LoginException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    @Override
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

    @Override
    @Transactional
    public TokenDto login(LoginRequestDto requestDto) throws Exception {
        User user = findUserByUsername(requestDto.getUsername());
        if(!passwordEncoder.matches(requestDto.getPassword(), user.getPassword())){
            throw new LoginException("비밀번호가 일치하지 않습니다.");
        }
        return jwtUtil.createToken(user.getUsername());

    }

    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new NoSuchElementException("유저가 존재하지 않습니다.")
        );
    }
}
