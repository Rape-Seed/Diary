package com.example.diary.global.auth.jwt;

import static com.example.diary.domain.member.entity.PlatForm.GOOGLE;
import static com.example.diary.domain.member.entity.PlatForm.find;

import com.example.diary.global.auth.dto.OAuthRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;

@Component
@RequiredArgsConstructor
public class OAuthFactory {

    private final GoogleInfo googleInfo;

    public OAuthRequestDto getRequest(String code, String platform) {
        if (find(platform) == GOOGLE) {
            return getGoogle(code);
        }
        return null;
    }

    public OAuthRequestDto getGoogle(String code) {
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();

        linkedMultiValueMap.add("grant_type", "authorization_code");
        linkedMultiValueMap.add("client_id", googleInfo.getGoogleClientId());
        linkedMultiValueMap.add("client_secret", googleInfo.getGoogleClientSecret());
        linkedMultiValueMap.add("redirect_uri", googleInfo.getGoogleRedirect());
        linkedMultiValueMap.add("code", code);

        return new OAuthRequestDto(googleInfo.getGoogleTokenUrl(), linkedMultiValueMap);
    }

    @Getter
    @Component
    static class GoogleInfo {
        @Value("${spring.social.google.client_id}")
        String googleClientId;
        @Value("${spring.social.google.redirect}")
        String googleRedirect;
        @Value("${spring.social.google.client_secret}")
        String googleClientSecret;
        @Value("${spring.social.google.url.token}")
        private String googleTokenUrl;
        @Value("${spring.social.google.url.profile}")
        private String googleProfileUrl;
    }
}
