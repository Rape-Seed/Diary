package com.example.diary.domain.emotion.entity;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class EmotionTypeTest {

    @ParameterizedTest
    @ValueSource(strings = {"positive", "Positive", "neutral", "negative"})
    void findMyEmo(String input) {
        Assertions.assertThat(EmotionType.myEmotion(input)).isEqualTo(String.valueOf(EmotionType.find(input)));
    }

    @ParameterizedTest
    @ValueSource(strings = {"positive", "Positive", "neutral", "negative"})
    void test(String input) {
        System.out.println(EmotionType.myEmotion(input));
    }


}