package com.sparta.todolistmanage.exception;

public class CommentNotFoundException extends RuntimeException{

    public CommentNotFoundException(String message) {
        super(message);
    }
}
