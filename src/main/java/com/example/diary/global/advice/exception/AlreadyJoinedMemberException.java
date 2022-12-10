package com.example.diary.global.advice.exception;

public class AlreadyJoinedMemberException extends RuntimeException {
    public AlreadyJoinedMemberException() {
    }

    public AlreadyJoinedMemberException(String message) {
        super(message);
    }

    public AlreadyJoinedMemberException(String message, Throwable cause) {
        super(message, cause);
    }
}
