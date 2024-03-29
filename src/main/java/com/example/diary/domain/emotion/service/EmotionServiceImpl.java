package com.example.diary.domain.emotion.service;

import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.emotion.dto.EmotionAnalyzeDto;
import com.example.diary.domain.emotion.dto.EmotionAnalyzeDto.Sentences;
import com.example.diary.domain.emotion.dto.EmotionRequestDto;
import com.example.diary.domain.emotion.dto.EmotionResponseDto;
import com.example.diary.domain.emotion.entity.DiaryEmotion;
import com.example.diary.domain.emotion.entity.EmotionType;
import com.example.diary.domain.emotion.entity.SentenceEmotion;
import com.example.diary.domain.emotion.repository.DiaryEmotionRepository;
import com.example.diary.domain.emotion.repository.SentenceEmotionRepository;
import com.example.diary.domain.recommend.entity.EmotionGenres;
import com.example.diary.domain.recommend.service.RecommendService;
import com.example.diary.global.advice.exception.DiaryNotFoundException;
import com.example.diary.global.properties.EmotionProperties;
import com.example.diary.global.utils.RandomUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EmotionServiceImpl implements EmotionService {

    private final EmotionProperties properties;
    private final RestTemplate restTemplate;

    private final DiaryRepository diaryRepository;
    private final RecommendService recommendService;
    private final DiaryEmotionRepository diaryEmotionRepository;
    private final SentenceEmotionRepository sentenceEmotionRepository;

    @Override
    @Transactional
    public EmotionResponseDto<?> analyzeDiary(EmotionRequestDto emotionRequestDto) {
        EmotionAnalyzeDto result = restTemplate.postForObject(properties.getApiUrl(),
                new HttpEntity<>(emotionRequestDto, makeHttpHeader()),
                EmotionAnalyzeDto.class);

        DiaryEmotion diaryEmotion = diaryEmotionRepository.save(result.toDiaryEmotion(
                emotionRequestDto.getContent(),
                diaryRepository.findById(emotionRequestDto.getDiaryId()).orElseThrow(DiaryNotFoundException::new)));

        for (Sentences sentences : result.getSentences()) {
            SentenceEmotion sentenceEmotion = result.toSentenceEmotion(sentences);
            sentenceEmotion.setDiaryEmotion(diaryEmotion);
            sentenceEmotionRepository.save(sentenceEmotion);
        }

        return randomRecommend(diaryEmotion);
    }

    private EmotionResponseDto<?> randomRecommend(DiaryEmotion diaryEmotion) {
        Long pick = RandomUtils.makeRandomNumber(0, 3);
        if (pick == 0) {
            return new EmotionResponseDto<>(
                    EmotionType.myEmotion(diaryEmotion.getSentiment()),
                    recommendService.recommendMovie(
                            EmotionGenres.valueOf(diaryEmotion.getSentiment().toUpperCase()))
            );
        }

        return new EmotionResponseDto<>(
                EmotionType.myEmotion(diaryEmotion.getSentiment()),
                recommendService.recommendPhrase()
        );

    }


    private HttpHeaders makeHttpHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(properties.getKeyIdName(), properties.getKeyIdUnique());
        httpHeaders.add(properties.getKeyName(), properties.getKeyUnique());
        httpHeaders.add("Content-Type", "application/json");
        return httpHeaders;
    }
}
