package com.sparta.todolistmanage.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDto {

    @NotBlank
    private String todoName;

    @NotBlank
    private String contents;

}
