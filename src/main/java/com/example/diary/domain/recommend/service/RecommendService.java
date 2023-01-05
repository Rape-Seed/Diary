package com.example.diary.domain.recommend.service;

import com.example.diary.domain.recommend.dto.MovieResponseDto;
import com.example.diary.domain.recommend.entity.EmotionGenres;
import org.springframework.web.multipart.MultipartFile;

public interface RecommendService {

    MovieResponseDto recommendMovie(EmotionGenres emotionGenres);

    void recommendPhrase();

    void uploadPhrase(MultipartFile file);
}
