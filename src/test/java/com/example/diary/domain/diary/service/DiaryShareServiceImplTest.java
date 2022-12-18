package com.example.diary.domain.diary.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.example.diary.domain.diary.dto.DiaryResponse;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
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
    DiaryShareServiceImpl diaryShareService;

    private Member me;
    private Member member1;
    private Team team1;
    private Team team2;
    private TeamMember teamMember;

    @BeforeEach
    void setUp() {
        Member newMember = Member.builder()
                .name("테스트")
                .birthday(LocalDate.of(2022, 1, 1))
                .email("test@test.com")
                .code("QWER12")
                .build();
        me = memberRepository.save(newMember);

        Member newMember1 = Member.builder()
                .name("테스트2")
                .birthday(LocalDate.of(2022, 1, 2))
                .email("test2@test.com")
                .code("QWER13")
                .build();
        member1 = memberRepository.save(newMember1);

        Team newTeam1 = Team.builder()
                .name("테스트 팀")
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

        TeamMember newTeamMember = TeamMember.builder()
                .team(team1)
                .member(me)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        teamMember = teamMemberRepository.save(newTeamMember);
        teamMember.setMember(me);
        teamMember.setTeam(team1);
    }

    @DisplayName("올바른 공유일기를 조회")
    @Test
    void getSharedDiary_success() throws Exception {
        //given
        TeamMember teamMember2 = TeamMember.builder()
                .team(team1)
                .member(member1)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        TeamMember saveTeamMember2 = teamMemberRepository.save(teamMember2);
        saveTeamMember2.setMember(member1);
        saveTeamMember2.setTeam(team1);

        Diary newDiary = Diary.builder()
                .content("오늘 행복했다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(me)
                .team(team1)
                .build();
        diaryRepository.save(newDiary);

        Diary diary2 = Diary.builder()
                .content("오늘 슬펐다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member1)
                .team(team1)
                .build();
        Diary saveDiary2 = diaryRepository.save(diary2);

        DiaryResponse diaryResponse = DiaryResponse.builder()
                .diaryId(saveDiary2.getId())
                .teamName(team1.getName())
                .memberName(member1.getName())
                .content(saveDiary2.getContent())
                .date(saveDiary2.getDate())
                .build();

        //when
        DiaryResponse sharedDiaryResponse = diaryShareService.getSharedDiary(saveDiary2.getId(), me);

        //then
        assertThat(sharedDiaryResponse).isEqualTo(diaryResponse);
    }

    @DisplayName("해당 팀 멤버가 아닌 경우 공유일기 조회 에러")
    @Test
    void getSharedDiary_NotTeamMember() throws Exception {
        //given
        TeamMember teamMember2 = TeamMember.builder()
                .team(team2)
                .member(member1)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        TeamMember saveTeamMember2 = teamMemberRepository.save(teamMember2);
        saveTeamMember2.setMember(member1);
        saveTeamMember2.setTeam(team2);

        Diary newDiary = Diary.builder()
                .content("오늘 행복했다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(me)
                .team(team1)
                .build();
        Diary saveDiary = diaryRepository.save(newDiary);

        Diary diary2 = Diary.builder()
                .content("오늘 슬펐다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member1)
                .team(team2)
                .build();
        Diary saveDiary2 = diaryRepository.save(diary2);

        //then
        assertThatThrownBy(() -> diaryShareService.getSharedDiary(saveDiary2.getId(), me))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }

    @DisplayName("본인이 일기를 쓰지 않은 경우 공유일기 조회 에러")
    @Test
    void getSharedDiary_NotWroteMyDiary() throws Exception {
        //given
        TeamMember teamMember2 = TeamMember.builder()
                .team(team1)
                .member(member1)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        TeamMember saveTeamMember2 = teamMemberRepository.save(teamMember2);
        saveTeamMember2.setMember(member1);
        saveTeamMember2.setTeam(team1);

        Diary diary2 = Diary.builder()
                .content("오늘 슬펐다.")
                .date(LocalDate.of(2022, 1, 3))
                .member(member1)
                .team(team1)
                .build();
        Diary saveDiary2 = diaryRepository.save(diary2);

        //then
        assertThatThrownBy(() -> diaryShareService.getSharedDiary(saveDiary2.getId(), me))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }

    @DisplayName("팀 멤버 수락을 하지 않은 경우 공유일기 조회 에러")
    @Test
    void getSharedDiary_NotAcceptTeamMember() throws Exception {
        //given
        teamMember.updateAcceptStatus(AcceptStatus.REJECT);

        TeamMember teamMember2 = TeamMember.builder()
                .team(team1)
                .member(member1)
                .acceptStatus(AcceptStatus.APPROVE)
                .build();
        TeamMember saveTeamMember2 = teamMemberRepository.save(teamMember2);
        saveTeamMember2.setMember(member1);
        saveTeamMember2.setTeam(team1);

        Diary newDiary = Diary.builder()
                .content("오늘 행복했다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(me)
                .team(team1)
                .build();
        Diary saveDiary = diaryRepository.save(newDiary);

        Diary diary2 = Diary.builder()
                .content("오늘 슬펐다.")
                .date(LocalDate.of(2022, 1, 2))
                .member(member1)
                .team(team1)
                .build();
        Diary saveDiary2 = diaryRepository.save(diary2);

        //then
        assertThatThrownBy(() -> diaryShareService.getSharedDiary(saveDiary2.getId(), me))
                .isInstanceOf(DiaryNotAuthorizedException.class);
    }
}
