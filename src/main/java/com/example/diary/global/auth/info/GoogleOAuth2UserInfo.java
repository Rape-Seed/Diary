package com.example.diary.global.auth.info;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
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
        return "2000-01-01";
    }

    @Override
    public String getProfileImage() {
        return (String) attributes.get("picture");
    }
}
