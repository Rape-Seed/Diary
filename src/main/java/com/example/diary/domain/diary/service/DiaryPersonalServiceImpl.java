package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import com.example.diary.global.advice.exception.DiaryNotFoundException;
import com.example.diary.global.advice.exception.WrongDateException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.TimeZone;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryPersonalServiceImpl implements DiaryPersonalService {

    private final DiaryRepository diaryRepository;
    private final DiaryService diaryService;

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(DiaryNotFoundException::new);
    }

    public LocalDateTime LongToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
    }

    @Transactional
    @Override
    public DiaryDto createPersonal(Member member, DiaryRequest diaryRequest) {

        checkAvailableDate(diaryRequest.getDate(), diaryRequest.getCurrentTime().toLocalDate());
        Diary newDiary = savePersonal(member, diaryRequest);
        return DiaryDto.builder()
                .diaryId(newDiary.getId())
                .memberName(member.getName())
                .content(newDiary.getContent())
                .date(newDiary.getDate())
                .build();
    }

    private Diary savePersonal(Member member, DiaryRequest diaryRequest) {
        Diary diary = Diary.builder()
                .content(diaryRequest.getContent())
                .member(member)
                .date(diaryRequest.getDate())
                .build();

        return diaryRepository.save(diary);
    }

    private void checkAvailableDate(LocalDate date, LocalDate currentDate) {
        if (date.isBefore(currentDate) || date.plusDays(1).isAfter(currentDate)) {
            throw new WrongDateException();
        }
    }

    @Transactional
    @Override
    public DiaryDto update(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member) {
        Diary diary = findDiaryById(diaryId);
        Diary updatedDiary = diaryService.update(diary, diaryUpdateRequest, member);
        return DiaryDto.ofPersonal(updatedDiary);
    }

    @Transactional
    @Override
    public Long delete(Long diaryId, Member member) {
        Diary diary = findDiaryById(diaryId);
        return diaryService.delete(diary, member);
    }

    @Override
    public DiaryDto get(Long diaryId, Member member) {
        Diary diary = findDiaryById(diaryId);
        checkDiaryWriter(diary.getMember(), member);
        return DiaryDto.ofPersonal(diary);
    }

    private static void checkDiaryWriter(Member diaryWriter, Member member) {
        if (!diaryWriter.equals(member)) {
            throw new DiaryNotAuthorizedException("[ERROR] 일기 작성자만 수정이 가능합니다.");
        }
    }
}
