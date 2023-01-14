package com.example.diary.domain.emotion.dto;

import lombok.Getter;

@Getter
public class EmotionRequestDto {

    private Long diaryId;
    private String content;

    public EmotionRequestDto() {
    }

    public EmotionRequestDto(Long diaryId, String content) {
        this.diaryId = diaryId;
        this.content = content;
    }
}
