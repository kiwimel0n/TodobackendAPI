package com.sparta.todolistmanage.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseMessage {

    private int httpStatus;
    private String message;
    private Object data;

}