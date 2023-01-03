package com.example.diary.domain.emotion.entity;

import lombok.Getter;

@Getter
public enum EmotionType {
    HAPPY("행복"),
    SOSO("중간"),
    SAD("슬픔"),
    NONE("판독불가");

    private String message;

    EmotionType(String message) {
    }
}
