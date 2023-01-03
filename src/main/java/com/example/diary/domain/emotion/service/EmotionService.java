package com.example.diary.domain.emotion.service;

import com.example.diary.domain.emotion.dto.EmotionRequestDto;
import com.example.diary.domain.emotion.dto.EmotionResponseDto;

public interface EmotionService {

    EmotionResponseDto analyzeDiary(EmotionRequestDto emotionRequestDto);
}
