package com.example.diary.global.auth.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.common.dto.ResponseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthService {
    ResponseDto<?> refreshToken(HttpServletRequest request, HttpServletResponse response, Member member);

}
