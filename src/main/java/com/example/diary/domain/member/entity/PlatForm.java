package com.example.diary.domain.member.entity;

import java.util.Arrays;

public enum PlatForm {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String platform;

    PlatForm(String platform) {
        this.platform = platform;
    }

    public static PlatForm find(String platform) {
        return Arrays.stream(values())
                .filter(value -> platform.equals(value.platform))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 플랫폼으로 로그인 하였습니다."));
    }
}
