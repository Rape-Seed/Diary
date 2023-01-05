package com.example.diary.global.advice.exception;

public class GenresNotFoundException extends RuntimeException {
    public GenresNotFoundException() {
    }

    public GenresNotFoundException(String message) {
        super(message);
    }

    public GenresNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
