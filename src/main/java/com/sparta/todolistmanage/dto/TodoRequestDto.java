package com.sparta.todolistmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TodoRequestDto {

    private String todoName;

    private String contents;

}
