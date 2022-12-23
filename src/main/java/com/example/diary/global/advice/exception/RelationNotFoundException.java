package com.example.diary.global.advice.exception;

public class RelationNotFoundException extends RuntimeException {
    public RelationNotFoundException() {
    }

    public RelationNotFoundException(String message) {
        super(message);
    }

    public RelationNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
