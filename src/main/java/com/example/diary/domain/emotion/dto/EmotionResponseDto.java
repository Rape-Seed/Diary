package com.example.diary.domain.emotion.dto;

import com.example.diary.domain.recommend.dto.MovieResponseDto;

public class EmotionResponseDto {

    private final String type;
    private final MovieResponseDto movieResponseDto;

    public EmotionResponseDto(String type, MovieResponseDto movieResponseDto) {
        this.type = type;
        this.movieResponseDto = movieResponseDto;
    }

    public String getType() {
        return type;
    }

    public MovieResponseDto getMovieResponseDto() {
        return movieResponseDto;
    }
}
