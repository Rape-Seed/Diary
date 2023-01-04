package com.example.diary.domain.emotion.entity;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum EmotionType {
    HAPPY("positive"),
    SO("neutral"),
    SAD("negative"),
    NONE("none");

    public static final Map<String, EmotionType> TYPES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(EmotionType::getMessage, Function.identity()))
    );

    public Map<String, EmotionType> getTypes() {
        return TYPES;
    }

    private final String message;

    EmotionType(String message) {
        this.message = message;
    }

    public static String myEmotion(String sentiment) {
        if (!TYPES.containsKey(sentiment.toLowerCase())) {
            return NONE.name();
        }
        return TYPES.get(sentiment.toLowerCase()).name();

    }

    public static EmotionType find(String sentiment) {
        return Arrays.stream(values())
                .filter(emotionType -> emotionType.getMessage().equals(sentiment.toLowerCase()))
                .findFirst()
                .orElse(NONE);
    }
}
