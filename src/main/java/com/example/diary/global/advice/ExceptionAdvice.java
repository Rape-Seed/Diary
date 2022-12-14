package com.example.diary.global.advice;

import com.example.diary.global.advice.exception.AlreadyJoinedMemberException;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import com.example.diary.global.advice.exception.DiaryNotFoundException;
import com.example.diary.global.advice.exception.FriendNotAuthorizedException;
import com.example.diary.global.advice.exception.GenresNotFoundException;
import com.example.diary.global.advice.exception.LoginFailureException;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exception.MovieNotFoundException;
import com.example.diary.global.advice.exception.RelationAlreadyExistException;
import com.example.diary.global.advice.exception.RelationAlreadyFormedException;
import com.example.diary.global.advice.exception.RelationNotFoundException;
import com.example.diary.global.advice.exception.TeamNotFoundException;
import com.example.diary.global.advice.exception.TokenValidFailedException;
import com.example.diary.global.advice.exception.WrongDateException;
import com.example.diary.global.advice.exceptionDto.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AlreadyJoinedMemberException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse alreadyJoinedMemberException() {
        return ExceptionResponse.getFailureResult(-101, "[ERROR] 이미 가입된 사용자입니다.");
    }

    @ExceptionHandler(LoginFailureException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse loginFailureException() {
        return ExceptionResponse.getFailureResult(-101, "아이디 혹은 비밀번호가 틀립니다.");
    }

    @ExceptionHandler(MemberNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse memberNotFoundException() {
        return ExceptionResponse.getFailureResult(-102, "해당 멤버를 찾을 수 없습니다.");
    }

    @ExceptionHandler(TokenValidFailedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse tokenValidFailedException() {
        return ExceptionResponse.getFailureResult(-103, "Failed to generate Token.");
    }


    @ExceptionHandler(DiaryNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse diaryNotFoundException() {
        return ExceptionResponse.getFailureResult(-201, "해당 일기를 찾을 수 없습니다.");
    }

    @ExceptionHandler(DiaryNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse diaryNotAuthorizedException() {
        return ExceptionResponse.getFailureResult(-202, "해당 일기의 접근 권한이 없습니다.");
    }

    @ExceptionHandler(GenresNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse genresNotFoundException() {
        return ExceptionResponse.getFailureResult(-401, "장르를 선택할 수 없습니다.");
    }

    @ExceptionHandler(MovieNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse movieNotFoundException() {
        return ExceptionResponse.getFailureResult(-402, "영화를 찾을 수 없습니다..");
    }

    @ExceptionHandler(FriendNotAuthorizedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse friendNotAuthorizedException() {
        return ExceptionResponse.getFailureResult(-501, "해당 사용자에 대한 접근 권한이 없습니다.");
    }

    @ExceptionHandler(RelationNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse relationNotFoundException() {
        return ExceptionResponse.getFailureResult(-502, "친구를 찾을 수 없습니다.");
    }

    @ExceptionHandler(RelationAlreadyExistException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse relationAlreadyExistException() {
        return ExceptionResponse.getFailureResult(-502, "친구를 찾을 수 없습니다.");
    }

    @ExceptionHandler(RelationAlreadyFormedException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse relationAlreadyFormedException() {
        return ExceptionResponse.getFailureResult(-503, "이미 친구인 사용자 입니다.");
    }

    @ExceptionHandler(TeamNotFoundException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse teamNotFoundException() {
        return ExceptionResponse.getFailureResult(-601, "해당 팀을 찾을 수 없습니다.");
    }

    @ExceptionHandler(WrongDateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionResponse wrongDateException() {
        return ExceptionResponse.getFailureResult(-901, "요청하신 날짜가 잘못되었습니다.");
    }
}
