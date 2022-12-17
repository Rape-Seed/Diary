package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryResponse;
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

    @Override
    public DiaryResponse getPersonal(Long diaryId, Member member) {
        Diary diary = findDiaryById(diaryId);
        checkAuthorization(member, diary);
        return DiaryResponse.builder()
                .diaryId(diary.getId())
                .memberName(diary.getMember().getName())
                .content(diary.getContent())
                .emotion(diary.getEmotion().toString())
                .build();
    }

    private Diary findDiaryById(Long diaryId) {
        return diaryRepository.findById(diaryId).orElseThrow(DiaryNotFoundException::new);
    }

    private void checkAuthorization(Member member, Diary diary) {
        if (!member.equals(diary.getMember())) {
            throw new DiaryNotAuthorizedException();
        }
    }

    public LocalDateTime LongToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
    }

    @Transactional
    @Override
    public DiaryResponse createPersonal(Member member, DiaryRequest diaryRequest) {

        checkAvailableDate(diaryRequest.getDate(), diaryRequest.getCurrentTime().toLocalDate());
        Diary newDiary = savePersonal(member, diaryRequest);
        return DiaryResponse.builder()
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
    public DiaryResponse updatePersonal(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member) {
        Diary diary = findDiaryById(diaryId);
        checkAuthorization(member, diary);
        diary.updateDiary(diaryUpdateRequest);
        return DiaryResponse.builder()
                .diaryId(diary.getId())
                .memberName(member.getName())
                .content(diary.getContent())
                .date(diary.getDate())
                .build();
    }

    @Transactional
    @Override
    public void deletePersonal(Long diaryId, Member member) {
        Diary diary = findDiaryById(diaryId);
        checkAuthorization(member, diary);
        diaryRepository.delete(diary);
    }
}
