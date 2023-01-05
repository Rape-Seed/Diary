package com.example.diary.domain.recommend.entity;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

class EmotionGenresTest {

    List<Integer> positive = List.of(
            28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770, 10752, 37);
    List<Integer> neutral = List.of(28, 12, 16, 35, 99, 18, 10751, 14, 36, 10402, 9648, 10749, 878, 10770, 37);
    List<Integer> negative = List.of(16, 35, 99, 18, 10751, 10402, 10749);

    //    @Test
    @RepeatedTest(value = 150)
    void getRandomGenreNeutral() {
        Integer genres = EmotionGenres.getGenres(EmotionGenres.NEUTRAL);
        assertTrue(neutral.contains(genres));
    }

    //    @Test
    @RepeatedTest(value = 150)
    void getRandomGenrePositive() {
        Integer genres = EmotionGenres.getGenres(EmotionGenres.POSITIVE);
        assertTrue(positive.contains(genres));
    }

    //    @Test
    @RepeatedTest(value = 150)
    void getRandomGenreNegative() {
        Integer genres = EmotionGenres.getGenres(EmotionGenres.NEGATIVE);
        assertTrue(negative.contains(genres));
    }

    @RepeatedTest(value = 20)
    void repeatTest() {
        System.out.println(EmotionGenres.getGenres(EmotionGenres.POSITIVE));
    }

    //    @RepeatedTest(value = 150)
    @Test
    void test() {
        Random random = new SecureRandom();
        random.setSeed(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
//        System.out.println(positive.size());
        System.out.println(random.nextInt(positive.size()));

    }


}