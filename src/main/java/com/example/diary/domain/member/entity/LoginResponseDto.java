package com.example.diary.domain.member.entity;


public class LoginResponseDto {
    private String email;
    private String accessToken;
    private String refreshToken;

    public LoginResponseDto(String email, String accessToken, String refreshToken) {
        this.email = email;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
