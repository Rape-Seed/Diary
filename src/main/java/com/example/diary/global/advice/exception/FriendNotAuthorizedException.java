package com.example.diary.global.advice.exception;

public class FriendNotAuthorizedException extends RuntimeException {
    public FriendNotAuthorizedException() {
    }

    public FriendNotAuthorizedException(String message) {
        super(message);
    }

    public FriendNotAuthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
