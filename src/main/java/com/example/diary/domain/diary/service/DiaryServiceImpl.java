package com.example.diary.domain.diary.service;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.diary.repository.DiaryRepository;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.advice.exception.DiaryNotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class DiaryServiceImpl implements DiaryService {

    private final DiaryRepository diaryRepository;

    public void checkDiaryWriter(Member diaryWriter, Member member) {
        if (!diaryWriter.equals(member)) {
            throw new DiaryNotAuthorizedException("[ERROR] 일기 작성자만 수정이 가능합니다.");
        }
    }

    @Transactional
    public DiaryDto update(Long diaryId, DiaryUpdateRequest diaryUpdateRequest, Member member) {
        Diary diary = diaryRepository.findPersonalDiaryById(diaryId);
        checkDiaryWriter(diary.getMember(), member);
        diary.updateDiary(diaryUpdateRequest);
        return DiaryDto.ofPersonal(diary);
    }
}
