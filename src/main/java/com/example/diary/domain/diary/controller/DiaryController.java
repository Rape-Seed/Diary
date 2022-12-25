package com.example.diary.domain.diary.controller;

import com.example.diary.domain.diary.dto.DiaryDto;
import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.diary.service.DiaryPersonalService;
import com.example.diary.domain.diary.service.DiaryShareService;
import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class DiaryController {

    private final DiaryShareService diaryShareService;
    private final DiaryPersonalService diaryPersonalService;

    @GetMapping("/v1/diary/my/{diaryId}")
    public ResponseDto<DiaryDto> getPersonalDiary(@PathVariable("diaryId") Long diaryId,
                                                  @CurrentMember Member member) {
        DiaryDto diaryDto = diaryPersonalService.get(diaryId, member);
        return new ResponseDto<>(diaryDto, HttpStatus.OK);
    }

    @PutMapping("/v1/diary/my/{diaryId}")
    public ResponseDto<DiaryDto> updatePersonalDiary(@PathVariable("diaryId") Long diaryId,
                                                     @RequestBody DiaryUpdateRequest diaryUpdateRequest,
                                                     @CurrentMember Member member) {
        DiaryDto diaryDto = diaryPersonalService.update(diaryId, diaryUpdateRequest, member);
        return new ResponseDto<>(diaryDto, HttpStatus.OK);
    }

    @DeleteMapping("/v1/diary/my")
    public ResponseDto<Long> deletePersonalDiary(@RequestHeader("Diary-Id") Long diaryId,
                                                 @CurrentMember Member member) {
        Long deletedDiaryId = diaryPersonalService.delete(diaryId, member);
        return new ResponseDto<>(deletedDiaryId, HttpStatus.OK);
    }

    @GetMapping("/v1/diary/share/{diaryId}")
    public ResponseDto<DiaryDto> getSharedDiary(@PathVariable("diaryId") Long diaryId,
                                                @CurrentMember Member member) {
        DiaryDto diaryDto = diaryShareService.getSharedDiary(diaryId, member);
        return new ResponseDto<>(diaryDto, HttpStatus.OK);
    }

    @PutMapping("/v1/diary/share/{diaryId}")
    public ResponseDto<DiaryDto> updateSharedDiary(@PathVariable("diaryId") Long diaryId,
                                                   @RequestBody DiaryUpdateRequest diaryUpdateRequest,
                                                   @CurrentMember Member member) {
        DiaryDto diaryDto = diaryShareService.update(diaryId, diaryUpdateRequest, member);
        return new ResponseDto<>(diaryDto, HttpStatus.OK);
    }

    @DeleteMapping("/v1/diary/share")
    public ResponseDto<Long> deleteSharedDiary(@RequestHeader("Diary-Id") Long diaryId,
                                               @CurrentMember Member member) {
        Long deletedDiaryId = diaryShareService.delete(diaryId, member);
        return new ResponseDto<>(deletedDiaryId, HttpStatus.OK);
    }
}
