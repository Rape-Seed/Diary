package com.example.diary.domain.team.service;

import com.example.diary.domain.team.dto.TeamInviteRequest;
import com.example.diary.domain.team.dto.TeamInviteResponse;

public interface TeamDiaryService {

    TeamInviteResponse inviteTeam(TeamInviteRequest teamInviteRequest);
}
