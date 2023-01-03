package com.example.diary.domain.emotion.dto;

import lombok.Getter;

@Getter
public class EmotionRequestDto {
    private String content;

    public EmotionRequestDto() {
    }

    public EmotionRequestDto(String content) {
        this.content = content;
    }
}
