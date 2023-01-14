package com.example.diary.domain.emotion.entity;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.emotion.repository.DiaryEmotionRepository;
import com.example.diary.domain.emotion.repository.EmotionRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DiaryEmotionTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    DiaryEmotionRepository diaryEmotionRepository;

    @Autowired
    EmotionRepository emotionRepository;

    @Test
    void saveTest() {

        Member member = makeMember("홍길동", "gil@gmail.com", "1q2w3e4r", "2000-01-01");
        Member member1 = memberRepository.save(member);

        Emotion newEmotion = Emotion.builder().content(EmotionType.HAPPY).build();
        Emotion emotion = emotionRepository.save(newEmotion);

        Diary newDiary1 = Diary.builder().content("오늘 행복했다.").date(LocalDate.of(2022, 1, 2))
                .member(member1).emotion(emotion).build();
        Diary saved = diaryRepository.save(newDiary1);

        DiaryEmotion diaryEmotion = DiaryEmotion.builder().content("오늘 행복했다.").sentiment("positive").diary(saved)
                .build();
        DiaryEmotion savedDiaryEmotion = diaryEmotionRepository.save(diaryEmotion);

        Diary diary = diaryRepository.findById(saved.getId()).orElseThrow();
        System.out.println(diary.getDiaryEmotion());
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