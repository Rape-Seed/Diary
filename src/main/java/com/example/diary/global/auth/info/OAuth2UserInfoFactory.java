package com.example.diary.global.auth.info;

import static com.example.diary.domain.member.entity.PlatformType.GOOGLE;
import static com.example.diary.domain.member.entity.PlatformType.NAVER;

import com.example.diary.domain.member.entity.PlatformType;
import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(PlatformType platformType, Map<String, Object> attributes) {
        if (platformType == GOOGLE) {
            return new GoogleOAuth2UserInfo(attributes);
        }
        if (platformType == NAVER) {
            return new NaverOAuth2UserInfo(attributes);
        }

        throw new IllegalArgumentException("[ERROR] Invalid PlatForm Info");
    }

}
