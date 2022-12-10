package com.example.diary.global.advice;

import com.example.diary.global.advice.exception.LoginFailureException;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exceptionDto.ExceptionResponse;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse memberNotFoundException() {
        return ExceptionResponse.getFailureResult(-103, "해당 멤버를 찾을 수 없습니다.");
    }

    @ExceptionHandler(TokenValidFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse tokenValidFailedException() {
        return ExceptionResponse.getFailureResult(-104, "Failed to generate Token.");
    }

    @ExceptionHandler(DiaryNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse diaryNotFoundException() {
        return ExceptionResponse.getFailureResult(-104, "해당 일기를 찾을 수 없습니다.");
    }

    @ExceptionHandler(DiaryNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse diaryNotAuthorizedException() {
        return ExceptionResponse.getFailureResult(-105, "해당 일기의 접근 권한이 없습니다.");
    }

    @ExceptionHandler(DiaryWrongDateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse diaryWrongDateException() {
        return ExceptionResponse.getFailureResult(-106, "해당 날짜의 일기를 작성할 수 없습니다.");
    }
}
