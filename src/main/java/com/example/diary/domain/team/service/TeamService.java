package com.example.diary.domain.team.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.dto.DiaryTeamResponse;
import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;
import com.example.diary.domain.team.dto.TeamReplyRequest;
import com.example.diary.domain.team.dto.TeamReplyResponse;
import java.time.LocalDate;
import java.util.List;

public interface TeamService {

    TeamInviteResponse inviteTeam(TeamInviteRequest teamInviteRequest, Member member);

    TeamReplyResponse replyTeam(TeamReplyRequest teamReplyRequest, Member member);

    List<DiaryTeamResponse> getTeams(Member member, LocalDate date);
}
