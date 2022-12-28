package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.member.entity.Member;
import java.time.LocalDateTime;

public interface DiaryPersonalService {

    LocalDateTime LongToLocalDateTime(Long time);

    DiaryDto update(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member);

    Long delete(Long diaryId, Member member);

    DiaryDto get(Long diaryId, Member member);
}
