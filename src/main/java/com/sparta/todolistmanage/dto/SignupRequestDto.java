package com.sparta.todolistmanage.dto;

import jakarta.validation.ValidationException;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;

    public SignupRequestDto(String username, String password){
        if(!isValidUsername(username) || !isValidPassword(password)){
            throw new ValidationException("잘못된 username 또는 password 입력입니다");
        }
        this.username = username;
        this.password = password;
    }

    private boolean isValidUsername(String username) {

        return username.matches("^[a-z0-9]{4,10}$");
    }

    private boolean isValidPassword(String password) {

        return password.matches("^[a-zA-Z0-9]{8,15}$");
    }
}
