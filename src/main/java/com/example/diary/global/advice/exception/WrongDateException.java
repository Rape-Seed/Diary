package com.example.diary.global.advice.exception;

public class WrongDateException extends RuntimeException {
    public WrongDateException() {
    }

    public WrongDateException(String message) {
        super(message);
    }

    public WrongDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
