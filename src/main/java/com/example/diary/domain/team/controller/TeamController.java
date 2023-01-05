package com.example.diary.domain.team.controller;

import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;
import com.example.diary.domain.team.dto.TeamReplyRequest;
import com.example.diary.domain.team.dto.TeamReplyResponse;
import com.example.diary.domain.team.service.TeamService;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TeamController {

    private final TeamService teamService;

    public ResponseDto<TeamInviteResponse> inviteTeam(TeamInviteRequest teamInviteRequest) {
        TeamInviteResponse teamInviteResponse = teamService.inviteTeam(teamInviteRequest);
        return new ResponseDto<>(teamInviteResponse, HttpStatus.OK);
    }

    public ResponseDto<TeamReplyResponse> replyTeam(TeamReplyRequest teamReplyRequest) {
        TeamReplyResponse teamReplyResponse = teamService.replyTeam(teamReplyRequest);
        return new ResponseDto<>(teamReplyResponse, HttpStatus.OK);
    }
}
