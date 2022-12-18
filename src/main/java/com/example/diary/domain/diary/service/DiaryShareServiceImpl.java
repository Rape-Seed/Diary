package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryResponse;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.AcceptStatus;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryShareServiceImpl implements DiaryShareService {

    private final DiaryRepository diaryRepository;

    private TeamMember findTeamMember(List<TeamMember> teamMembers, Member member) {
        for (TeamMember teamMember : teamMembers) {
            if (member.equals(teamMember.getMember())) {
                return teamMember;
            }
        }
        throw new DiaryNotAuthorizedException("[ERROR] 해당 팀의 멤버가 아닙니다.");
    }

    private void checkAcceptStatus(TeamMember teamMember) {
        if (!teamMember.getAcceptStatus().equals(AcceptStatus.APPROVE)) {
            throw new DiaryNotAuthorizedException("[ERROR] 팀요청을 수락해야합니다");
        }
    }

    private void checkWroteDiary(Team team, Member member, LocalDate date) {
        if (diaryRepository.findDiaryByTeamAndMemberAndDate(team, member, date) == null) {
            throw new DiaryNotAuthorizedException("[ERROR] 팀원의 일기를 조회 전에 자신의 일기를 작성해주세요");
        }
    }

    @Override
    public DiaryResponse getSharedDiary(Long diaryId, Member member) {
        Diary diary = diaryRepository.findDiaryByDiaryId(diaryId);
        checkAcceptStatus(findTeamMember(diary.getTeam().getTeamMembers(), member));
        checkWroteDiary(diary.getTeam(), member, diary.getDate());
        return DiaryResponse.builder()
                .diaryId(diary.getId())
                .memberName(diary.getMember().getName())
                .teamName(diary.getTeam().getName())
                .content(diary.getContent())
                .date(diary.getDate())
                .build();
    }
}
