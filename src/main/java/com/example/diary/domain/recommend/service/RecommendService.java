package com.example.diary.domain.recommend.service;

import com.example.diary.domain.recommend.dto.MovieResponseDto;
import com.example.diary.domain.recommend.dto.PhraseRegRequestDto;
import com.example.diary.domain.recommend.dto.PhraseRegResponseDto;
import com.example.diary.domain.recommend.dto.PhraseResponseDto;
import com.example.diary.domain.recommend.entity.EmotionGenres;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public interface RecommendService {

    MovieResponseDto recommendMovie(EmotionGenres emotionGenres);

    PhraseResponseDto recommendPhrase();

    PhraseRegResponseDto registerPhrase(PhraseRegRequestDto phraseRegRequestDto);

    PhraseRegResponseDto registerPhraseByExcel(MultipartFile file) throws IOException;
}
