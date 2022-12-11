package com.example.diary.global.common.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ResponseDto<T> {

    private T response;

    private String message;

    private HttpStatus status;

    public ResponseDto(String message, HttpStatus status) {
        this(null, message, status);
    }

    public ResponseDto(T response, HttpStatus status) {
        this(response, "", status);
    }

    public ResponseDto(T response, String message, HttpStatus status) {
        this.response = response;
        this.message = message;
        this.status = status;
    }
}
