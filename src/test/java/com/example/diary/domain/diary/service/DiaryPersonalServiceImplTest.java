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
class DiaryPersonalServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    EmotionRepository emotionRepository;
    @Autowired
    DiaryPersonalServiceImpl diaryPersonalService;

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

    @DisplayName("개인일기를 정상적으로 조회합니다.")
    @Test
    void updateSharedDiary_success() throws Exception {
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
        DiaryDto updateDiaryDto = diaryPersonalService.update(diary1.getId(), diaryUpdateRequest, member1);

        //then
        assertThat(diaryDto).isEqualTo(updateDiaryDto);
        assertThat(updateContent).isEqualTo(diaryRepository.findById(diary1.getId()).get().getContent());
    }

    @DisplayName("작성자가 아닌 경우 일기 수정시 오류가 발생한다.")
    @Test
    void updateDiary_NotWriter() throws Exception {
        //given
        String updateContent = "update test content";
        DiaryUpdateRequest diaryUpdateRequest = new DiaryUpdateRequest(updateContent);

        //then
        assertThatThrownBy(() -> diaryPersonalService.update(diary1.getId(), diaryUpdateRequest, member2))
                .isInstanceOf(DiaryNotAuthorizedException.class);
        assertThat(diary1.getContent()).isEqualTo(diaryRepository.findById(diary1.getId()).get().getContent());
    }

    @DisplayName("공유일기를 정상적으로 삭제합니다.")
    @Test
    void deleteDiary_success() throws Exception {
        //given
        Long diaryId = diary1.getId();

        //when
        Long deleteDiaryId = diaryPersonalService.delete(diaryId, member1);

        //then
        assertThat(diaryId).isEqualTo(deleteDiaryId);
        assertThat(false).isEqualTo(diaryRepository.findById(diaryId).isPresent());
    }

    @DisplayName("작성자가 아닌 경우 공유일기 삭제시 오류가 발생한다.")
    @Test
    void deleteDiary_NotWriter() throws Exception {
        //given
        Long diaryId = diary1.getId();

        //then
        assertThatThrownBy(() -> diaryPersonalService.delete(diary1.getId(), member2))
                .isInstanceOf(DiaryNotAuthorizedException.class);
        assertThat(true).isEqualTo(diaryRepository.findById(diaryId).isPresent());
    }
}