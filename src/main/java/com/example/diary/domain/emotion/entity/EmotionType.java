package com.example.diary.domain.emotion.entity;

import lombok.Getter;

@Getter
public enum EmotionType {
    HAPPY("행복");

    private String message;

    EmotionType(String message) {
    }
}
