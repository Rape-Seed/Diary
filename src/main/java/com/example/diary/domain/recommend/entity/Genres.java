package com.example.diary.domain.recommend.entity;

import com.example.diary.global.advice.exception.GenresNotFoundException;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Genres {
    Action(28, "Action"),
    Adventure(12, "Adventure"),
    Animation(16, "Animation"),
    Comedy(35, "Comedy"),
    Crime(80, "Crime"),
    Documentary(99, "Documentary"),
    Drama(18, "Drama"),
    Family(10751, "Family"),
    Fantasy(14, "Fantasy"),
    History(36, "History"),
    Horror(27, "Horror"),
    Music(10402, "Music"),
    Mystery(9648, "Mystery"),
    Romance(10749, "Romance"),
    ScienceFiction(878, "Science Fiction"),
    TVMovie(10770, "TV Movie"),
    Thriller(53, "Thriller"),
    War(10752, "War"),
    Western(37, "Western");

    private static final Map<Integer, Genres> GENRES = Collections.unmodifiableMap(
            Stream.of(values()).collect(Collectors.toMap(Genres::getGenreNumber, Function.identity()))
    );

    private final Integer genreNumber;
    private final String genreName;

    Genres(Integer genreNumber, String genreName) {
        this.genreNumber = genreNumber;
        this.genreName = genreName;
    }

    public Integer getGenreNumber() {
        return genreNumber;
    }

    public String getGenreName() {
        return genreName;
    }

    public static Genres findGenre(Integer genreNumber) {
        if (!GENRES.containsKey(genreNumber)) {
            throw new GenresNotFoundException();
        }
        return GENRES.get(genreNumber);
    }
}
