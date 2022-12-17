package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryResponse;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.member.entity.Member;
import java.time.LocalDateTime;

public interface DiaryPersonalService {

    LocalDateTime LongToLocalDateTime(Long time);

    DiaryResponse createPersonal(Member member, DiaryRequest diaryRequest);

    DiaryResponse getPersonal(Long diaryId, Member member);

    DiaryResponse updatePersonal(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member);

    void deletePersonal(Long diaryId, Member member);
}
