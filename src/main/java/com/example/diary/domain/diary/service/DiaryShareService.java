package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.member.entity.Member;

public interface DiaryShareService {

    DiaryDto getSharedDiary(Long diaryId, Member member);

    DiaryDto update(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member);

    Long delete(Long diaryId, Member member);
}
