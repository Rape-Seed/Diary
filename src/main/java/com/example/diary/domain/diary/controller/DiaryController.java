package com.example.diary.domain.diary.controller;

import com.example.diary.domain.diary.dto.DiaryRequest;
import com.example.diary.domain.diary.dto.DiaryResponse;
import com.example.diary.domain.diary.service.DiaryService;
import com.example.diary.domain.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class DiaryController {

    private DiaryService diaryService;

    @PostMapping("/v1/diary")
    public ResponseEntity<DiaryResponse> createPersonal(
            @RequestAttribute Long currentTime,
            @RequestBody DiaryRequest diaryRequest,
            Member member) {
        diaryRequest.setCurrentTime(diaryService.LongToLocalDateTime(currentTime));
        DiaryResponse diaryResponse = diaryService.createPersonal(member, diaryRequest);
        return new ResponseEntity<>(diaryResponse, HttpStatus.CREATED);
    }
}
