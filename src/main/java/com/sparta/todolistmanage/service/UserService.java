package com.sparta.todolistmanage.service;

import com.sparta.todolistmanage.dto.request.LoginRequestDto;
import com.sparta.todolistmanage.dto.request.SignupRequestDto;
import com.sparta.todolistmanage.dto.response.TokenDto;

public interface UserService {

    /**
     * 사용자 회원가입
     * @param requestDto 회원가입 요청 정보
     */
    void signup(SignupRequestDto requestDto);

    TokenDto login(LoginRequestDto requestDto) throws Exception;
}
