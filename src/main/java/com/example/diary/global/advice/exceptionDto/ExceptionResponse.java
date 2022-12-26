package com.example.diary.global.advice.exceptionDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExceptionResponse {
    private boolean success;
    private int status;
    private String message;

    public static ExceptionResponse getFailureResult(int status, String message) {
        ExceptionResponse exceptionResponse = new ExceptionResponse();
        exceptionResponse.setSuccess(false);
        exceptionResponse.setStatus(status);
        exceptionResponse.setMessage(message);

        return exceptionResponse;
    }
}
