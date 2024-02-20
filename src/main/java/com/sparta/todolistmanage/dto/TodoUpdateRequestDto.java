package com.sparta.todolistmanage.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoUpdateRequestDto {

    private String todoName;

    private String contents;
}
