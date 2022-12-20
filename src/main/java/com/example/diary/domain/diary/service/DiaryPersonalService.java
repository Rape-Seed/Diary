package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.member.entity.Member;
import java.time.LocalDateTime;

public interface DiaryPersonalService {

    LocalDateTime LongToLocalDateTime(Long time);

    DiaryDto createPersonal(Member member, DiaryRequest diaryRequest);

    DiaryDto getPersonal(Long diaryId, Member member);

    DiaryDto update(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member);

    Long delete(Long diaryId, Member member);
}
