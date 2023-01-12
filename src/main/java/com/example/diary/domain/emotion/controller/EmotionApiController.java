package com.example.diary.domain.emotion.controller;

import com.example.diary.domain.emotion.dto.EmotionRequestDto;
import com.example.diary.domain.emotion.dto.EmotionResponseDto;
import com.example.diary.domain.emotion.service.EmotionService;
import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class EmotionApiController {

    private final EmotionService emotionService;

    @PostMapping("/v1/emotion")
    public ResponseDto<EmotionResponseDto<?>> analyzeMyDiaryEmotion(
            @RequestBody EmotionRequestDto emotionRequestDto,
            @CurrentMember Member member) {
        return new ResponseDto<>(emotionService.analyzeDiary(emotionRequestDto), "감정을 분석했습니다.", HttpStatus.OK);
    }

    /**
     * TODO 감정 분석 시 다이어리 랑 1대1 매핑해주기 -> test, 상원 <- 거의 완료
     * TODO 분석한 감정 한달단위로 가져오기 - 하연
     * TODO 월별, 주별 감정분석 리포트 - batch, 상원
     * TODO 팀구성, 친구추가 알림메세지 - 상원
     * TODO 일기 적으라는 알람 설정 - batch, 상원
     */
}


