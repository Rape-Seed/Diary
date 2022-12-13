package com.example.diary.global.auth.info;

import com.example.diary.global.utils.RandomUtils;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class KakaoOAuth2UserInfo extends OAuth2UserInfo {
    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getName() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("nickname");
    }

    @Override
    public String getEmail() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");
        String email = (String) properties.get("email");
        if (email == null) {
            return RandomUtils.make(10) + "@diaryEmotion.online";
        }
        return email;
    }

    @Override
    public String getBirthday() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("kakao_account");
        String birthday = (String) properties.get("birthday");
        if (birthday == null) {
            return "2000-01-01";
        }
        return "1900-" + birthday.substring(0, 2) + "-" + birthday.substring(2);
    }

    @Override
    public String getProfileImage() {
        Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
        return (String) properties.get("profile_image");
    }
}
