package com.example.diary.domain.emotion.dto;

public class EmotionResponseDto {

    private final String type;

    public EmotionResponseDto(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
