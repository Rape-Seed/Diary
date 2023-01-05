package com.example.diary.domain.recommend.entity;

import com.example.diary.global.advice.exception.GenresNotFoundException;
import com.example.diary.global.utils.RandomUtils;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum EmotionGenres {

    POSITIVE(
            List.of(28, 12, 16, 35, 80, 99, 18, 10751, 14, 36, 27, 10402, 9648, 10749, 878, 10770, 10752, 37)),
    NEUTRAL(
            List.of(28, 12, 16, 35, 99, 18, 10751, 14, 36, 10402, 9648, 10749, 878, 10770, 37)),
    NEGATIVE(
            List.of(16, 35, 99, 18, 10751, 10402, 10749)
    );

    private static final Map<EmotionGenres, List<Integer>> emoGenres = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Function.identity(), EmotionGenres::getGenreList))
    );

    private final List<Integer> genreList;

    EmotionGenres(List<Integer> genreList) {
        this.genreList = genreList;
    }

    public List<Integer> getGenreList() {
        return genreList;
    }

    public static EmotionGenres find(String emotion) {
        return EmotionGenres.valueOf(emotion.toUpperCase());
    }

    public static Integer getGenres(EmotionGenres emotionGenres) {
        if (emoGenres.get(emotionGenres) == null) {
            throw new GenresNotFoundException();
        }
        List<Integer> genres = emoGenres.get(emotionGenres);

        return genres.get(RandomUtils.makeRandomNumber(genres.size()));
    }

}
