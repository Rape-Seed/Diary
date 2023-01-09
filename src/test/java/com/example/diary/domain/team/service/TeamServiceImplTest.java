package com.example.diary.domain.team.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;
import com.example.diary.domain.team.repository.TeamMemberRepository;
import com.example.diary.domain.team.repository.TeamRepository;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TeamServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    TeamRepository teamRepository;
    @Autowired
    TeamMemberRepository teamMemberRepository;
    @Autowired
    TeamServiceImpl teamService;

    private Member member1;
    private Member member2;

    @BeforeEach
    void setUp() {
        Member newMember1 = Member.builder()
                .name("테스트1")
                .birthday(LocalDate.of(2022, 1, 1))
                .email("test1@test.com")
                .code(RandomUtils.make(6))
                .build();
        member1 = memberRepository.save(newMember1);

        Member newMember2 = Member.builder()
                .name("테스트2")
                .birthday(LocalDate.of(2022, 1, 1))
                .email("test2@test.com")
                .code(RandomUtils.make(6))
                .build();
        member2 = memberRepository.save(newMember2);
    }

    @DisplayName("올바른 팀 초대를 합니다.")
    @Test
    void inviteTeam_success() throws Exception {
        //given
        TeamInviteRequest teamInviteRequest = TeamInviteRequest.builder()
                .teamName("테스트 팀1")
                .startDate(LocalDate.of(2022, 1, 1))
                .endDate(LocalDate.of(2022, 1, 2))
                .friends(List.of(member2.getId()))
                .currentTime(LocalDateTime.of(2022, 1, 1, 10, 13))
                .build();

        //when
        TeamInviteResponse teamInviteResponse = teamService.inviteTeam(teamInviteRequest, member1);

        //then
        assertThat(teamInviteResponse.getFriends().get(0).getMemberId()).isEqualTo(member2.getId());
        assertThat(teamInviteResponse.getStartDate()).isEqualTo(LocalDate.of(2022, 1, 1));
    }
}