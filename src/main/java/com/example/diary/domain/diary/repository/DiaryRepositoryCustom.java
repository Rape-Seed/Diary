package com.example.diary.domain.diary.repository;

import com.example.diary.domain.diary.entity.Diary;
import java.time.LocalDate;

public interface DiaryRepositoryCustom {

    Diary findDiaryByMemberIdAndTeamIdAndDate(Long memberId, Long teamId, LocalDate date);
}
