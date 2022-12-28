package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryCreateResponseDto;
import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.Team;
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
        if (!date.isEqual(currentDate) && !date.minusDays(1).isEqual(currentDate)) {
            throw new WrongDateException();
        }
    }

    public DiaryCreateResponseDto create(DiaryRequest diaryRequest, Member member) {
        checkAvailableDate(diaryRequest.getDate(), diaryRequest.getCurrentTime().toLocalDate());
        List<DiaryDto> diaries = new ArrayList<>();
        if (diaryRequest.getScope().getTeams() != null) {
            diaries.addAll(createSharedDiary(diaryRequest, member));
        }
        if (diaryRequest.getScope().isPersonal()) {
            diaries.add(createPersonalDiary(diaryRequest, member));
        }
        return new DiaryCreateResponseDto(diaries);
    }

    private DiaryDto createPersonalDiary(DiaryRequest diaryRequest, Member member) {
        Diary newDiary = Diary.builder()
                .content(diaryRequest.getContent())
                .member(member)
                .date(diaryRequest.getDate())
                .build();
        Diary savedDiary = diaryRepository.save(newDiary);
        return DiaryDto.ofPersonal(savedDiary);
    }

    public List<DiaryDto> createSharedDiary(DiaryRequest diaryRequest, Member member) {
        List<Team> teams = teamRepository.findTeamsById(diaryRequest.getScope().getTeams());
        List<DiaryDto> diaries = new ArrayList<>();
        for (Team team : teams) {
            Diary newDiary = Diary.builder()
                    .content(diaryRequest.getContent())
                    .member(member)
                    .team(team)
                    .date(diaryRequest.getDate())
                    .build();
            Diary savedDiary = diaryRepository.save(newDiary);
            diaries.add(DiaryDto.ofShared(savedDiary));
        }
        return diaries;
    }
}
