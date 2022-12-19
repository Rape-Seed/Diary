package com.example.diary.domain.diary.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.emotion.entity.Emotion;
import com.example.diary.domain.emotion.entity.EmotionType;
import com.example.diary.domain.emotion.repository.EmotionRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
class DiaryServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    EmotionRepository emotionRepository;
    @Autowired
    DiaryServiceImpl diaryService;

    private Member member1;
    private Member member2;
    private Emotion emotion;
    private Diary diary1;

    @BeforeEach
    void setUp() {
        Member newMember1 = Member.builder()
                .name("테스트")
                .birthday(LocalDate.of(2022, 1, 1))
                .email("test@test.com")
                .code("QWER12")
                .build();
        member1 = memberRepository.save(newMember1);

        Member newMember2 = Member.builder()
                .name("테스트2")
                .birthday(LocalDate.of(2022, 1, 2))
                .email("test2@test.com")
                .code("QWER13")
                .build();
        member2 = memberRepository.save(newMember2);

        Emotion newEmotion = Emotion.builder()
                .content(EmotionType.HAPPY)
                .build();
        emotion = emotionRepository.save(newEmotion);

        Diary newDiary1 = Diary.builder()
                .content("오늘 행복했다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member1)
                .emotion(emotion)
                .build();
        diary1 = diaryRepository.save(newDiary1);
    }

    @DisplayName("정상적인 일기 수정")
    @Test
    void updateDiary_success() throws Exception {
        //given
        String updateContent = "update test content";
        DiaryUpdateRequest diaryUpdateRequest = new DiaryUpdateRequest(updateContent);
        DiaryDto diaryDto = DiaryDto.builder()
                .diaryId(diary1.getId())
                .content(updateContent)
                .memberName(member1.getName())
                .emotion(emotion.getContent().getMessage())
                .date(diary1.getDate())
                .build();
        //when
        DiaryDto updateDiaryDto = diaryService.update(diary1.getId(), diaryUpdateRequest, member1);

        //then
        assertThat(updateDiaryDto).isEqualTo(diaryDto);
    }

    @DisplayName("작성자가 아닌 경우 일기 수정 예외처리")
    @Test
    void updateDiary_NotWriter() throws Exception {
        //given
        String updateContent = "update test content";
        DiaryUpdateRequest diaryUpdateRequest = new DiaryUpdateRequest(updateContent);

        //then
        assertThatThrownBy(() -> diaryService.update(diary1.getId(), diaryUpdateRequest, member2))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }
}
