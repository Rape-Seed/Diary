package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.member.entity.Member;

public interface DiaryService {

    Diary update(Diary diary, DiaryUpdateRequest diaryUpdateRequest, Member member);
}
