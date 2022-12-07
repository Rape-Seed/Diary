package com.example.diary.global.advice.exception;

public class TokenValidFailedException extends RuntimeException {
    public TokenValidFailedException() {
    }

    public TokenValidFailedException(String message) {
        super(message);
    }

    public TokenValidFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
