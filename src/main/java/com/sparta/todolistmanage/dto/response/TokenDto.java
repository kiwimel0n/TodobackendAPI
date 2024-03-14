package com.sparta.todolistmanage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenDto {

    String username;
    String accessToken;
    String refreshToken;

}
