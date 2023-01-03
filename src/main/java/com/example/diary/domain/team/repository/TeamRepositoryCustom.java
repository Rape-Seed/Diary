package com.example.diary.domain.team.repository;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.dto.DiaryTeamRequest;
import com.example.diary.domain.team.dto.DiaryTeamResponse;
import com.example.diary.domain.team.entity.Team;
import java.util.List;

public interface TeamRepositoryCustom {

    List<DiaryTeamResponse> findTeamsByMemberAndDate(Member selectMember, DiaryTeamRequest diaryTeamRequest);

    List<Team> findTeamsById(List<Long> teamIds);
}
