package com.example.diary.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class TMDBProperties {

    @Value("${app.tmdb.keyId}")
    private String tmdbKey;

    @Value("${app.tmdb.discover.url}")
    private String discoverUrl;


}
