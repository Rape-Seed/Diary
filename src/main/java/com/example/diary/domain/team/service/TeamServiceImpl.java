package com.example.diary.domain.team.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.team.dto.DiaryTeamResponse;
import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;
import com.example.diary.domain.team.dto.TeamReplyRequest;
import com.example.diary.domain.team.dto.TeamReplyResponse;
import com.example.diary.domain.team.entity.AcceptStatus;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import com.example.diary.domain.team.repository.TeamMemberRepository;
import com.example.diary.domain.team.repository.TeamRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exception.TeamNotFoundException;
import com.example.diary.global.advice.exception.WrongDateException;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final TeamMemberRepository teamMemberRepository;
    private final MemberRepository memberRepository;

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private List<Member> getMembersById(List<Long> memberIds) {
        List<Member> members = new ArrayList<>();
        for (Long memberId : memberIds) {
            members.add(findMemberById(memberId));
        }
        return members;
    }

    private Team saveTeam(TeamInviteRequest teamInviteRequest) {
        Team team = Team.builder()
                .code(RandomUtils.make(6))
                .name(teamInviteRequest.getTeamName())
                .startDate(teamInviteRequest.getStartDate())
                .endDate(teamInviteRequest.getEndDate())
                .build();

        return teamRepository.save(team);
    }

    private List<TeamMember> getTeamMembers(Team team, List<Member> members) {
        List<TeamMember> teamMembers = new ArrayList<>();
        for (Member member : members) {
            teamMembers.add(saveTeamMember(team, member));
        }
        return teamMembers;
    }

    private TeamMember saveTeamMember(Team team, Member member) {
        TeamMember teamMember = TeamMember.builder()
                .member(member)
                .team(team)
                .acceptStatus(AcceptStatus.WAITING)
                .build();

        return teamMemberRepository.save(teamMember);
    }

    private void checkAvailableDate(LocalDate date, LocalDate currentDate) {
        if (date.isBefore(currentDate) || date.plusDays(1).isAfter(currentDate)) {
            throw new WrongDateException();
        }
    }

    private void checkStartDate(LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            throw new WrongDateException();
        }
    }

    @Transactional
    @Override
    public TeamInviteResponse inviteTeam(TeamInviteRequest teamInviteRequest, Member member) {
//        checkAvailableDate(teamInviteRequest.getStartDate(), teamInviteRequest.getCurrentTime().toLocalDate());
        checkStartDate(teamInviteRequest.getStartDate(), teamInviteRequest.getEndDate());
        Team team = saveTeam(teamInviteRequest);
        saveTeamMember(team, member);
        List<Member> friends = getMembersById(teamInviteRequest.getFriends());
        List<TeamMember> teamMembers = getTeamMembers(team, friends);
        return new TeamInviteResponse(teamMembers, team);
    }

    private Team findTeamById(Long teamId) {
        return teamRepository.findById(teamId).orElseThrow(TeamNotFoundException::new);
    }

    @Transactional
    @Override
    public TeamReplyResponse replyTeam(TeamReplyRequest teamReplyRequest, Member member) {
        Team team = findTeamById(teamReplyRequest.getTeamId());
        TeamMember teamMember = teamMemberRepository.findTeamMemberByTeamAndMember(team, member);
        teamMember.updateAcceptStatus(teamReplyRequest.getAcceptStatus());
        return TeamReplyResponse.builder()
                .teamId(team.getId())
                .teamName(team.getName())
                .memberId(member.getId())
                .memberName(member.getName())
                .acceptStatus(teamMember.getAcceptStatus())
                .build();
    }

    public List<DiaryTeamResponse> getTeams(Member member, LocalDate date) {
        return teamRepository.findTeamsByMemberAndDate(member, date);
    }
}
