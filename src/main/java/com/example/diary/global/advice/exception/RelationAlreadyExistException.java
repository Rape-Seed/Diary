package com.example.diary.global.advice.exception;

public class RelationAlreadyExistException extends RuntimeException {
    public RelationAlreadyExistException() {
    }

    public RelationAlreadyExistException(String message) {
        super(message);
    }

    public RelationAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
