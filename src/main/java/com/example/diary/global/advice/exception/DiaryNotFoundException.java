package com.example.diary.global.advice.exception;

public class DiaryNotFoundException extends RuntimeException {
    public DiaryNotFoundException() {
    }

    public DiaryNotFoundException(String message) {
        super(message);
    }

    public DiaryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
