package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryResponse;
import com.example.diary.domain.member.entity.Member;

public interface DiaryShareService {

    DiaryResponse getSharedDiary(Long diaryId, Member member);
    
}
