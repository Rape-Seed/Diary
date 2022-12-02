package com.example.diary.global.auth.jwt;

import com.example.diary.global.auth.dto.OAuthRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthTokenProvider {

    private final RestTemplate restTemplate;
    private final OAuthFactory oAuthFactory;

    public void getSocialToken(String code, String platForm) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        OAuthRequestDto oAuthRequestDto = oAuthFactory.getRequest(code, platForm);
        HttpEntity<LinkedMultiValueMap<String, String>> request = new HttpEntity<>(oAuthRequestDto.getMap(),
                httpHeaders);

        ResponseEntity<String> response = restTemplate.postForEntity(oAuthRequestDto.getUrl(), request, String.class);


    }
}
