package com.example.diary.domain.member.entity;

import java.util.Arrays;

public enum PlatformType {
    GOOGLE("google"),
    NAVER("naver"),
    KAKAO("kakao");

    private final String platform;

    PlatformType(String platform) {
        this.platform = platform;
    }

    public static PlatformType find(String provider) {
        return Arrays.stream(values())
                .filter(value -> provider.equals(value.platform))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 잘못된 플랫폼으로 로그인 하였습니다."));
    }
}
