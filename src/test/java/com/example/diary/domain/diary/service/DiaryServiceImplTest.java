package com.example.diary.domain.diary.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.diary.domain.diary.dto.DiaryCreateResponseDto;
import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryRequest.Scope;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.emotion.entity.Emotion;
import com.example.diary.domain.emotion.entity.EmotionType;
import com.example.diary.domain.emotion.repository.EmotionRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.team.entity.AcceptStatus;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import com.example.diary.domain.team.repository.TeamMemberRepository;
import com.example.diary.domain.team.repository.TeamRepository;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import com.example.diary.global.advice.exception.WrongDateException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Transactional
@SpringBootTest
class DiaryServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    DiaryRepository diaryRepository;
    @Autowired
    EmotionRepository emotionRepository;
    @Autowired
    DiaryServiceImpl diaryService;

    @Autowired
    EntityManager em;

    private Member member1;
    private Member member2;
    private Emotion emotion;
    private Diary diary1;
    private Team team1;
    private TeamMember teamMember1;

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

        Team newTeam1 = Team.builder()
                .name("테스트 팀")
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 1, 3))
                .build();
        team1 = teamRepository.save(newTeam1);

        TeamMember newTeamMember1 = TeamMember.builder()
                .team(team1)
                .member(member1)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        teamMember1 = teamMemberRepository.save(newTeamMember1);
        teamMember1.setMember(member1);
        teamMember1.setTeam(team1);
    }

    @DisplayName("정상적으로 일기를 수정한다.")
    @Test
    void updateDiary_success() throws Exception {
        //given
        String updateContent = "update test content";
        DiaryUpdateRequest diaryUpdateRequest = new DiaryUpdateRequest(updateContent);

        //when
        Diary updateDiary = diaryService.update(diary1, diaryUpdateRequest, member1);

        //then
        assertThat(diary1).isEqualTo(updateDiary);
    }

    @DisplayName("작성자가 아닌 경우 일기 수정시 오류가 발생한다.")
    @Test
    void updateDiary_NotWriter() throws Exception {
        //given
        String updateContent = "update test content";
        DiaryUpdateRequest diaryUpdateRequest = new DiaryUpdateRequest(updateContent);

        //then
        assertThatThrownBy(() -> diaryService.update(diary1, diaryUpdateRequest, member2))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }

    @DisplayName("일기를 정상적으로 삭제다.")
    @Test
    void deleteDiary_success() throws Exception {
        //given
        Long diaryId = diary1.getId();

        //when
        Long deleteDiaryId = diaryService.delete(diary1, member1);

        //then
        assertThat(diaryId).isEqualTo(deleteDiaryId);
        assertThat(false).isEqualTo(diaryRepository.findById(diaryId).isPresent());
    }

    @DisplayName("작성자가 아닌 경우 일기 삭제시 오류가 발생한다.")
    @Test
    void deleteDiary_NotWriter() throws Exception {
        //given
        Long diaryId = diary1.getId();

        //then
        assertThatThrownBy(() -> diaryService.delete(diary1, member2))
                .isInstanceOf(DiaryNotAuthorizedException.class);
        assertThat(true).isEqualTo(diaryRepository.findById(diaryId).isPresent());
    }

    @DisplayName("개인일기를 정상적으로 생성한다.")
    @Test
    void createPersonalDiary_success() throws Exception {
        //given
        String content = "테스트 content";
        DiaryRequest diaryRequest = DiaryRequest.builder()
                .content(content)
                .scope(new Scope(true))
                .currentTime(LocalDateTime.of(2022, 1, 1, 10, 13))
                .date(LocalDate.of(2022, 1, 1))
                .build();
        //when
        DiaryCreateResponseDto diaryCreateResponseDto = diaryService.create(diaryRequest, member1);

        //then
        assertThat(content).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getContent());
        assertThat(LocalDate.of(2022, 1, 1)).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getDate());
    }

    @DisplayName("공유일기를 정상적으로 생성한다.")
    @Test
    void createSharedDiary_success() throws Exception {
        //given
        String content = "테스트 content";
        DiaryRequest diaryRequest = DiaryRequest.builder()
                .content(content)
                .scope(new Scope(List.of(team1.getId())))
                .currentTime(LocalDateTime.of(2022, 1, 1, 10, 13))
                .date(LocalDate.of(2022, 1, 1))
                .build();
        //when
        DiaryCreateResponseDto diaryCreateResponseDto = diaryService.create(diaryRequest, member1);

        //then
        assertThat(content).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getContent());
        assertThat(LocalDate.of(2022, 1, 1)).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getDate());
        assertThat(team1.getName()).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getTeamName());
    }

    @DisplayName("개인일기와 공유일기를 정상적으로 생성한다.")
    @Test
    void createSharedAndPersonalDiary_success() throws Exception {
        //given
        String content = "테스트 content";
        DiaryRequest diaryRequest = DiaryRequest.builder()
                .content(content)
                .scope(new Scope(true, List.of(team1.getId())))
                .currentTime(LocalDateTime.of(2022, 1, 1, 10, 13))
                .date(LocalDate.of(2022, 1, 1))
                .build();

        //when
        DiaryCreateResponseDto diaryCreateResponseDto = diaryService.create(diaryRequest, member1);

        //then
        assertThat(2).isEqualTo(diaryCreateResponseDto.getDiaries().size());
        assertThat(content).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getContent());
        assertThat(LocalDate.of(2022, 1, 1)).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getDate());
    }

    @DisplayName("해당 팀 멤버가 아닌 경우 공유일기 생성시 예외처리가 발생한다.")
    @Test
    void createSharedDiary_notTeamMember() throws Exception {
        //given
        String content = "테스트 content";
        DiaryRequest diaryRequest = DiaryRequest.builder()
                .content(content)
                .scope(new Scope(List.of(team1.getId())))
                .currentTime(LocalDateTime.of(2022, 1, 1, 10, 13))
                .date(LocalDate.of(2022, 1, 1))
                .build();
        //then
        assertThatThrownBy(() -> diaryService.create(diaryRequest, member2))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }

    @DisplayName("다음날 전일 일기를 작성할 경우 정상적으로 일기를 작성한다.")
    @Test
    void createSharedDiary_rightDate() throws Exception {
        //given
        String content = "테스트 content";
        DiaryRequest diaryRequest = DiaryRequest.builder()
                .content(content)
                .scope(new Scope(List.of(team1.getId())))
                .currentTime(LocalDateTime.of(2022, 1, 2, 10, 13))
                .date(LocalDate.of(2022, 1, 1))
                .build();

        //when
        DiaryCreateResponseDto diaryCreateResponseDto = diaryService.create(diaryRequest, member1);

        //then
        assertThat(content).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getContent());
        assertThat(content).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getContent());
        assertThat(LocalDate.of(2022, 1, 1)).isEqualTo(diaryCreateResponseDto.getDiaries().get(0).getDate());
    }

    @DisplayName("이틀 전 일기를 작성할 경우 예외처리가 발생한다.")
    @Test
    void createSharedDiary_wrongDate() throws Exception {
        //given
        String content = "테스트 content";
        DiaryRequest diaryRequest = DiaryRequest.builder()
                .content(content)
                .scope(new Scope(List.of(team1.getId())))
                .currentTime(LocalDateTime.of(2022, 1, 3, 10, 13))
                .date(LocalDate.of(2022, 1, 1))
                .build();
        //then
        assertThatThrownBy(() -> diaryService.create(diaryRequest, member2))
                .isInstanceOf(WrongDateException.class);
    }
}
