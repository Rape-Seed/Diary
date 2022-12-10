package com.example.diary.domain.team.dto;

import com.example.diary.domain.team.entity.AcceptStatus;
import lombok.Data;

@Data
public class TeamReplyRequest {

    private Long teamId;

    private Long memberId;

    private AcceptStatus acceptStatus;

}
