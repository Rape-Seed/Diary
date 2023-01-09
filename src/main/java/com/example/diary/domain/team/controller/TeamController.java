package com.example.diary.domain.team.controller;

import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.dto.DiaryTeamResponse;
import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;
import com.example.diary.domain.team.dto.TeamReplyRequest;
import com.example.diary.domain.team.dto.TeamReplyResponse;
import com.example.diary.domain.team.service.TeamService;
import com.example.diary.global.common.dto.ResponseDto;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    @PostMapping("/diary/share/invite")
    public ResponseDto<TeamInviteResponse> inviteTeam(@RequestBody TeamInviteRequest teamInviteRequest,
                                                      @CurrentMember Member member) {
        TeamInviteResponse teamInviteResponse = teamService.inviteTeam(teamInviteRequest, member);
        return new ResponseDto<>(teamInviteResponse, HttpStatus.OK);
    }

    @PostMapping("/diary/share/reply")
    public ResponseDto<TeamReplyResponse> replyTeam(@RequestBody TeamReplyRequest teamReplyRequest,
                                                    @CurrentMember Member member) {
        TeamReplyResponse teamReplyResponse = teamService.replyTeam(teamReplyRequest);
        return new ResponseDto<>(teamReplyResponse, HttpStatus.OK);
    }

    @PostMapping("/diary/share")
    public ResponseDto<List<DiaryTeamResponse>> getTeams(@RequestParam("Date") LocalDate date,
                                                         @CurrentMember Member member) {
        List<DiaryTeamResponse> teamResponse = teamService.getTeams(member, date);
        return new ResponseDto<>(teamResponse, HttpStatus.OK);
    }
}
