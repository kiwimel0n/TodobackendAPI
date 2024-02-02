package com.sparta.todolistmanage.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {

    @NotBlank
    @Pattern(regexp = "^[a-z0-9]{4,10}$")
    private String username;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$")
    private String password;
}
