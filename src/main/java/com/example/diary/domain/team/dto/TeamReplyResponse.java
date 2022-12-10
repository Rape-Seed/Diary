package com.example.diary.domain.team.dto;

import com.example.diary.domain.team.entity.AcceptStatus;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TeamReplyResponse {

    private Long teamId;

    private String teamName;

    private Long memberId;

    private String memberName;

    private AcceptStatus acceptStatus;

    public TeamReplyResponse(Long teamId, String teamName, Long memberId, String memberName,
                             AcceptStatus acceptStatus) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.memberId = memberId;
        this.memberName = memberName;
        this.acceptStatus = acceptStatus;
    }
}
