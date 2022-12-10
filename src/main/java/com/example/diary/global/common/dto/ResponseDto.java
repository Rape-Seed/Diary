package com.example.diary.global.common.dto;

import org.springframework.http.HttpStatus;

public class ResponseDto<T> {

    private T response;

    private String message;

    private HttpStatus status;

    public ResponseDto(T response, HttpStatus status) {
        this(response, "", status);
    }

    public ResponseDto(T response, String message, HttpStatus status) {
        this.response = response;
        this.message = message;
        this.status = status;
    }
}
