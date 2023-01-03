package com.example.diary.global.properties;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmotionProperties {

    @Value("${app.naver.emotion.keyId.name}")
    private String keyIdName;

    @Value("${app.naver.emotion.keyId.unique}")
    private String keyIdUnique;

    @Value("${app.naver.emotion.key.name}")
    private String keyName;

    @Value("${app.naver.emotion.key.unique}")
    private String keyUnique;

    @Value("${app.naver.emotion.url}")
    private String apiUrl;
}
