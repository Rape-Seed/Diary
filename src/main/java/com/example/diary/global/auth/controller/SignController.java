package com.example.diary.global.auth.controller;

import com.example.diary.global.auth.dto.LoginResponseDto;
import com.example.diary.global.auth.dto.SocialCodeDto;
import com.example.diary.global.auth.service.SignService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sign")
public class SignController {

    private final Environment environment;

    private final SignService signService;

    @PostMapping("/login/{platform}")
    public ResponseEntity<?> loginBySocial(@RequestBody SocialCodeDto socialCode, @PathVariable String platform) {
        LoginResponseDto loginResponseDto = signService.loginMemberBySocial(socialCode.getSocialCode(), platform);
        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }
}
