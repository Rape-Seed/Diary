package com.example.diary.global.advice.exception;

public class DiaryWrongDateException extends RuntimeException {
    public DiaryWrongDateException() {
    }

    public DiaryWrongDateException(String message) {
        super(message);
    }

    public DiaryWrongDateException(String message, Throwable cause) {
        super(message, cause);
    }
}
