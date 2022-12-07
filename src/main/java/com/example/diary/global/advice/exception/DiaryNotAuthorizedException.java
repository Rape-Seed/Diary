package com.example.diary.global.advice.exception;

public class DiaryNotAuthorizedException extends RuntimeException {
    public DiaryNotAuthorizedException() {
    }

    public DiaryNotAuthorizedException(String message) {
        super(message);
    }

    public DiaryNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
