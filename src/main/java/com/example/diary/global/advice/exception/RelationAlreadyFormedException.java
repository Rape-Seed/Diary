package com.example.diary.global.advice.exception;

public class RelationAlreadyFormedException extends RuntimeException {
    public RelationAlreadyFormedException() {
    }

    public RelationAlreadyFormedException(String message) {
        super(message);
    }

    public RelationAlreadyFormedException(String message, Throwable cause) {
        super(message, cause);
    }
}
