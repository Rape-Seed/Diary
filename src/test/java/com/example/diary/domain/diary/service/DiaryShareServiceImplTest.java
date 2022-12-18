package com.example.diary.domain.diary.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.diary.domain.diary.dto.DiaryResponse;
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
import java.time.LocalDate;
import javax.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
class DiaryShareServiceImplTest {

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
    DiaryShareServiceImpl diaryShareService;

    private Member member1;
    private Member member2;
    private Team team1;
    private Team team2;
    private TeamMember teamMember1;
    private TeamMember teamMember2;
    private Emotion emotion;
    private Diary diary1;
    private Diary diary2;

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

        Team newTeam1 = Team.builder()
//                .name("테스트 팀")
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 1, 3))
                .build();
        team1 = teamRepository.save(newTeam1);

        Team newTeam2 = Team.builder()
                .name("테스트 팀2")
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 1, 3))
                .build();
        team2 = teamRepository.save(newTeam2);

        TeamMember newTeamMember1 = TeamMember.builder()
                .team(team1)
                .member(member1)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        teamMember1 = teamMemberRepository.save(newTeamMember1);
        teamMember1.setMember(member1);
        teamMember1.setTeam(team1);

        TeamMember newTeamMember2 = TeamMember.builder()
                .team(team1)
                .member(member2)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        teamMember2 = teamMemberRepository.save(newTeamMember2);
        teamMember2.setMember(member2);
        teamMember2.setTeam(team1);

        Emotion newEmotion = Emotion.builder()
                .content(EmotionType.HAPPY)
                .build();
        emotion = emotionRepository.save(newEmotion);

        Diary newDiary1 = Diary.builder()
                .content("오늘 행복했다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member1)
                .team(team1)
                .emotion(emotion)
                .build();
        diary1 = diaryRepository.save(newDiary1);

        Diary newDiary2 = Diary.builder()
                .content("오늘 슬펐다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member2)
                .team(team1)
                .emotion(emotion)
                .build();
        diary2 = diaryRepository.save(newDiary2);
    }

    @DisplayName("올바른 공유일기를 조회")
    @Test
    void getSharedDiary_success() throws Exception {
        //given
        DiaryResponse diaryResponse = DiaryResponse.builder()
                .diaryId(diary2.getId())
                .teamName(team1.getName())
                .memberName(member2.getName())
                .content(diary2.getContent())
                .date(diary2.getDate())
                .emotion(emotion.getContent().toString())
                .build();

        //when
        DiaryResponse sharedDiaryResponse = diaryShareService.getSharedDiary(diary2.getId(), member1);

        //then
        assertThat(sharedDiaryResponse).isEqualTo(diaryResponse);
    }

    @DisplayName("해당 팀 멤버가 아닌 경우 공유일기 조회 에러")
    @Test
    void getSharedDiary_NotTeamMember() throws Exception {
        //given
        Member newMember3 = Member.builder()
                .name("테스트3")
                .birthday(LocalDate.of(2022, 1, 2))
                .email("test3@test.com")
                .code("QWER14")
                .build();
        Member member3 = memberRepository.save(newMember3);

        TeamMember newTeamMember3 = TeamMember.builder()
                .team(team2)
                .member(member3)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        TeamMember teamMember3 = teamMemberRepository.save(newTeamMember3);
        teamMember3.setMember(member3);
        teamMember3.setTeam(team2);

        Diary newDiary3 = Diary.builder()
                .content("오늘 슬펐다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member3)
                .team(team2)
                .emotion(emotion)
                .build();
        Diary diary3 = diaryRepository.save(newDiary3);

        //then
        assertThatThrownBy(() -> diaryShareService.getSharedDiary(diary2.getId(), member3))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }

    @DisplayName("본인이 일기를 쓰지 않은 경우 공유일기 조회 에러")
    @Test
    void getSharedDiary_NotWroteMyDiary() throws Exception {
        //given
        Member newMember3 = Member.builder()
                .name("테스트3")
                .birthday(LocalDate.of(2022, 1, 2))
                .email("test3@test.com")
                .code("QWER14")
                .build();
        Member member3 = memberRepository.save(newMember3);

        TeamMember newTeamMember3 = TeamMember.builder()
                .team(team1)
                .member(member3)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        TeamMember teamMember3 = teamMemberRepository.save(newTeamMember3);
        teamMember3.setMember(member3);
        teamMember3.setTeam(team1);

        //then
        assertThatThrownBy(() -> diaryShareService.getSharedDiary(diary2.getId(), member3))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }

    @DisplayName("팀 멤버 수락을 하지 않은 경우 공유일기 조회 에러")
    @Test
    void getSharedDiary_NotAcceptTeamMember() throws Exception {
        //given
        teamMember1.updateAcceptStatus(AcceptStatus.REJECT);

        //then
        assertThatThrownBy(() -> diaryShareService.getSharedDiary(diary2.getId(), member1))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }
}
