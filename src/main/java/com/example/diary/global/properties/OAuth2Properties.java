package com.example.diary.global.properties;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class OAuth2Properties {

    private List<String> authorizedRedirectUris = new ArrayList<>();

    public List<String> getAuthorizedRedirectUris() {
        return authorizedRedirectUris;
    }

    public OAuth2Properties authorizedRedirectUris(List<String> authorizedRedirectUris) {
        this.authorizedRedirectUris = authorizedRedirectUris;
        return this;
    }
}
