package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.AcceptStatus;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import com.example.diary.global.advice.exception.DiaryNotFoundException;
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

    private final DiaryService diaryService;

    private Diary findById(Long diaryId) {
        Diary diary = diaryRepository.findSharedDiaryById(diaryId);
        if (diary == null) {
            throw new DiaryNotFoundException("[ERROR] 해당 공유 일기를 찾을 수 없습니다.");
        }
        return diary;
    }

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
    public DiaryDto getSharedDiary(Long diaryId, Member member) {
        Diary diary = findById(diaryId);
        checkAcceptStatus(findTeamMember(diary.getTeam().getTeamMembers(), member));
        checkWroteDiary(diary.getTeam(), member, diary.getDate());
        return DiaryDto.ofShared(diary);
    }

    @Transactional
    @Override
    public DiaryDto update(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member) {
        Diary diary = findById(diaryId);
        diaryService.update(diary, diaryUpdateRequest, member);
        return DiaryDto.ofShared(diary);
    }
}
