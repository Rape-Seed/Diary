package com.example.diary.domain.emotion.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.emotion.dto.EmotionRequestDto;
import com.example.diary.domain.emotion.dto.EmotionResponseDto;
import com.example.diary.domain.emotion.entity.Emotion;
import com.example.diary.domain.emotion.entity.EmotionType;
import com.example.diary.domain.emotion.repository.DiaryEmotionRepository;
import com.example.diary.domain.emotion.repository.EmotionRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.recommend.entity.Phrase;
import com.example.diary.domain.recommend.repository.PhraseRepository;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class EmotionServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    DiaryEmotionRepository diaryEmotionRepository;
    @Autowired
    EmotionRepository emotionRepository;
    @Autowired
    PhraseRepository phraseRepository;
    @Autowired
    EmotionService emotionService;

    private Diary savedDiary;

    @BeforeEach
    void setup() {
        Member member1 = memberRepository.save(
                makeMember("홍길동", "gil@gmail.com", "1q2w3e44214r", "2000-01-01"));

        Emotion emotion = emotionRepository.save(Emotion.builder().content(EmotionType.HAPPY).build());

        Diary diary = Diary.builder().content("오늘 행복했다.").date(LocalDate.of(2022, 1, 2))
                .member(member1).emotion(emotion).build();
        savedDiary = diaryRepository.save(diary);

        phraseRepository.save(new Phrase("나는 바보다1"));
        phraseRepository.save(new Phrase("나는 바보다2"));
        phraseRepository.save(new Phrase("나는 바보다3"));
        phraseRepository.save(new Phrase("나는 바보다4"));
        phraseRepository.save(new Phrase("나는 바보다5"));
    }


    @Test
    void analyzeDiaryTest() {
        EmotionResponseDto<?> emotionResponseDto = emotionService.analyzeDiary(
                new EmotionRequestDto(savedDiary.getId(), savedDiary.getContent()));

        Diary diary = diaryRepository.findById(savedDiary.getId()).orElseThrow();

        assertThat(diary.getId()).isEqualTo(savedDiary.getId());
        assertThat(diary.getDiaryEmotion().getContent()).isEqualTo("오늘 행복했다.");
    }

    private Member makeMember(String name, String email, String code, String birthday) {
        return Member.builder()
                .name(name)
                .email(email)
                .code(RandomUtils.make())
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .profileImage("test")
                .platform(PlatformType.GOOGLE)
                .role(Role.MEMBER)
                .build();
    }


}