package com.example.diary.domain.emotion.controller;

import com.example.diary.domain.emotion.dto.EmotionRequestDto;
import com.example.diary.domain.emotion.dto.EmotionResponseDto;
import com.example.diary.domain.emotion.service.EmotionService;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmotionApiController {

    private final EmotionService emotionService;

    @PostMapping("/v1/emotion")
    public ResponseDto<EmotionResponseDto<?>> analyzeMyDiaryEmotion(@RequestBody EmotionRequestDto emotionRequestDto) {
        return new ResponseDto<>(emotionService.analyzeDiary(emotionRequestDto), "감정을 분석했습니다.", HttpStatus.OK);
    }
}


