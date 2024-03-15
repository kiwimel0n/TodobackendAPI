package com.sparta.todolistmanage.exception;

public class TodoNotFoundException extends RuntimeException{
    public TodoNotFoundException(String message) {
        super(message);
    }
}
