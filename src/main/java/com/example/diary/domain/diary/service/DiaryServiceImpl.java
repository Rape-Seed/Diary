package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryCreateResponseDto;
import com.example.diary.domain.diary.dto.DiaryCreateResponseDto.DiaryCreateDto;
import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.AcceptStatus;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import com.example.diary.domain.team.repository.TeamRepository;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import com.example.diary.global.advice.exception.WrongDateException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;
    private final TeamRepository teamRepository;

    public void checkDiaryWriter(Member diaryWriter, Member member) {
        if (!diaryWriter.equals(member)) {
            throw new DiaryNotAuthorizedException("[ERROR] 일기 작성자만 접근이 가능합니다.");
        }
    }

    @Transactional
    @Override
    public Diary update(Diary diary, DiaryUpdateRequest diaryUpdateRequest, Member member) {
        checkDiaryWriter(diary.getMember(), member);
        diary.updateDiary(diaryUpdateRequest);
        return diary;
    }

    @Transactional
    @Override
    public Long delete(Diary diary, Member member) {
        checkDiaryWriter(diary.getMember(), member);
        diaryRepository.deleteById(diary.getId());
        return diary.getId();
    }

    private void checkAvailableDate(LocalDate date, LocalDate currentDate) {
        if (!currentDate.isEqual(date) && !currentDate.minusDays(1).isEqual(date)) {
            throw new WrongDateException();
        }
    }

    public void checkAcceptStatus(TeamMember teamMember) {
        if (!teamMember.getAcceptStatus().equals(AcceptStatus.APPROVE)) {
            throw new DiaryNotAuthorizedException("[ERROR] 팀요청을 수락해야합니다");
        }
    }

    public TeamMember findTeamMember(List<TeamMember> teamMembers, Member member) {
        for (TeamMember teamMember : teamMembers) {
            if (member.equals(teamMember.getMember())) {
                return teamMember;
            }
        }
        throw new DiaryNotAuthorizedException("[ERROR] 해당 팀의 멤버가 아닙니다.");
    }

    public DiaryCreateResponseDto create(DiaryRequest diaryRequest, Member member) {
        checkAvailableDate(diaryRequest.getDate(), diaryRequest.getCurrentTime().toLocalDate());
        List<DiaryCreateDto> diaries = new ArrayList<>();
        if (diaryRequest.getScope().getTeams() != null) {
            diaries.addAll(createSharedDiary(diaryRequest, member));
        }
        if (diaryRequest.getScope().isPersonal()) {
            diaries.add(createPersonalDiary(diaryRequest, member));
        }
        return new DiaryCreateResponseDto(diaries);
    }

    private DiaryCreateDto createPersonalDiary(DiaryRequest diaryRequest, Member member) {
        Diary newDiary = Diary.builder()
                .content(diaryRequest.getContent())
                .member(member)
                .date(diaryRequest.getDate())
                .build();
        Diary savedDiary = diaryRepository.save(newDiary);
        return DiaryCreateDto.ofPersonal(savedDiary);
    }

    public List<DiaryCreateDto> createSharedDiary(DiaryRequest diaryRequest, Member member) {
        List<Team> teams = teamRepository.findTeamsById(diaryRequest.getScope().getTeams());
        List<DiaryCreateDto> diaries = new ArrayList<>();
        for (Team team : teams) {
            checkAcceptStatus(findTeamMember(team.getTeamMembers(), member));
            Diary newDiary = Diary.builder()
                    .content(diaryRequest.getContent())
                    .member(member)
                    .team(team)
                    .date(diaryRequest.getDate())
                    .build();
            Diary savedDiary = diaryRepository.save(newDiary);
            diaries.add(DiaryCreateDto.ofShared(savedDiary));
        }
        return diaries;
    }
}
