package com.example.diary.domain.emotion.dto;

public class EmotionResponseDto<T> {

    private final String type;
    private final T recommendResponseDto;

    public EmotionResponseDto(String type, T recommendResponseDto) {
        this.type = type;
        this.recommendResponseDto = recommendResponseDto;
    }

    public String getType() {
        return type;
    }

    public T getRecommendResponseDto() {
        return recommendResponseDto;
    }
}
