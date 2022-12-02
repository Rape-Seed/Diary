package com.example.diary.global.auth.service;

import com.example.diary.global.auth.dto.LoginResponseDto;

public interface SignService {
    LoginResponseDto loginMemberBySocial(String socialCode, String platform);
}
