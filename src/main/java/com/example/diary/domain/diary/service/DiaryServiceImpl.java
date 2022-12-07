package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryResponse;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.advice.exception.DiaryWrongDateException;
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
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    public LocalDateTime LongToLocalDateTime(Long time) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(time), TimeZone.getDefault().toZoneId());
    }

    @Transactional
    public DiaryResponse createPersonal(Member member, DiaryRequest diaryRequest) {

        checkAvailableDate(diaryRequest.getDate(), diaryRequest.getCurrentTime().toLocalDate());
        Diary newDiary = savePersonal(member, diaryRequest);
        return DiaryResponse.builder()
                .diaryId(newDiary.getId())
                .member(member.getName())
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
            throw new DiaryWrongDateException();
        }
    }
}
