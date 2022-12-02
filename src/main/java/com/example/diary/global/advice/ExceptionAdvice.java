package com.example.diary.global.advice;


import com.example.diary.global.advice.exception.InvalidRefreshTokenException;
import com.example.diary.global.advice.exception.LoginFailureException;
import com.example.diary.global.advice.exceptionDto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse loginFailureException() {
        return ExceptionResponse.getFailureResult(-102, "아이디 혹은 비밀번호가 틀립니다.");
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse accessDeniedException() {
        return ExceptionResponse.getFailureResult(-103, "권한이 필요합니다.");
    }

    @ExceptionHandler(InvalidRefreshTokenException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse invalidRefreshTokenException() {
        return ExceptionResponse.getFailureResult(-104, "Refresh Token이 유효하지 않습니다.");
    }

}
