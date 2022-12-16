package com.example.diary.domain.diary.repository;

import com.example.diary.domain.diary.entity.Diary;

public interface DiaryRepositoryCustom {

    Diary findDiaryByDiaryId(Long diaryId);
}
