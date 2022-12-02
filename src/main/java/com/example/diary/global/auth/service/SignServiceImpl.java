package com.example.diary.global.auth.service;

import com.example.diary.global.auth.dto.LoginResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignServiceImpl implements SignService {


    @Override
    public LoginResponseDto loginMemberBySocial(String socialCode, String platform) {
        return null;
    }
}
