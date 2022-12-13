package com.example.diary.domain.team.service;

import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;
import com.example.diary.domain.team.dto.TeamReplyRequest;
import com.example.diary.domain.team.dto.TeamReplyResponse;

public interface TeamService {

    TeamInviteResponse inviteTeam(TeamInviteRequest teamInviteRequest);

    TeamReplyResponse replyTeam(TeamReplyRequest teamReplyRequest);
}
