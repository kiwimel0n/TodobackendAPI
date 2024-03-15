package com.sparta.todolistmanage.controller;

import com.sparta.todolistmanage.dto.request.LoginRequestDto;
import com.sparta.todolistmanage.dto.request.SignupRequestDto;
import com.sparta.todolistmanage.dto.response.ResponseMessage;
import com.sparta.todolistmanage.dto.response.TokenDto;
import com.sparta.todolistmanage.jwt.JwtUtil;
import com.sparta.todolistmanage.service.UserServiceImpl;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final UserServiceImpl userService;
    private final JwtUtil jwtUtil;

    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> signup(@Valid @RequestBody SignupRequestDto requestDto, BindingResult bindingResult) {
        //Validation 예외처리
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest()
                    .body(ResponseMessage.builder()
                            .httpStatus(HttpStatus.BAD_REQUEST.value())
                            .message(bindingResult.getAllErrors().toString())
                            .build());
        }
        userService.signup(requestDto);

        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("회원가입 성공")
                        .build());
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> login(
            @RequestBody LoginRequestDto requestDto,
            HttpServletResponse response) throws Exception {
        TokenDto tokenDto = userService.login(requestDto);
        jwtUtil.accessTokenSetHeader(tokenDto.getAccessToken(),response);
        jwtUtil.addJwtRefreshTokenToCookie(tokenDto.getRefreshToken(),response);


        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .message("로그인 성공")
                        .build());
    }

    @PostMapping("/refresh")
    public ResponseEntity<ResponseMessage> recreateAccessToken(
            HttpServletRequest request,
            HttpServletResponse response
    ){

        String refreshToken = jwtUtil.getRefreshTokenFromRequest(request);
        System.out.println(refreshToken);
        if(jwtUtil.validateRefreshToken(refreshToken)) {
            String token = jwtUtil.substringToken(refreshToken);
            Claims info = jwtUtil.getUserInfoFromToken(token);
            String accessToken = jwtUtil.createAccessToken(info.getSubject());
            System.out.println("새로만들어진 엑세스 토큰  " + accessToken);
            jwtUtil.accessTokenSetHeader(accessToken, response);

        }
        return ResponseEntity.ok()
                .body(ResponseMessage.builder()
                        .httpStatus(HttpStatus.OK.value())
                        .build());
    }

}
