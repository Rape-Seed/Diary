package com.example.diary.global.auth.info;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class NaverOAuth2UserInfo extends OAuth2UserInfo {

    public NaverOAuth2UserInfo(Map<String, Object> attributes) {
        super((Map<String, Object>) attributes.get("response"));
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getBirthday() {
        return ((String) attributes.get("birthyear") + "-" + (String) attributes.get("birthday"));
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("profile_image");
    }
}
