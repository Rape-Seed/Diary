package com.example.diary.global.properties;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oAuth2 = new OAuth2();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Auth {
        @Value("${app.auth.tokenSecret}")
        private String tokenSecret;

        @Value("${app.auth.accessTokenExpiry}")
        private Long accessTokenExpiry;

        @Value("${app.auth.refreshTokenExpiry}")
        private Long refreshTokenExpiry;
    }

    public static final class OAuth2 {
        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }
}
