package com.example.diary.global.auth.controller;

import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.auth.service.AuthService;
import com.example.diary.global.common.dto.ResponseDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class AuthApiController {

    private final AuthService authService;

    @PostMapping("/v1/sign/refresh")
    public ResponseEntity<ResponseDto<?>> refreshTokens(HttpServletRequest request,
                                                        HttpServletResponse response,
                                                        @CurrentMember Member member) {
        return new ResponseEntity<>(authService.refreshToken(request, response, member), HttpStatus.OK);
    }

    @PostMapping("/v1/sign/out")
    public ResponseEntity<ResponseDto<?>> signOut(HttpServletRequest request,
                                                  HttpServletResponse response,
                                                  @CurrentMember Member member) {
        return new ResponseEntity<>(authService.signOut(request, response, member), HttpStatus.OK);
    }
}
