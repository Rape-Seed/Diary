package com.example.diary.domain.recommend.controller;

import com.example.diary.domain.recommend.dto.PhraseRegRequestDto;
import com.example.diary.domain.recommend.dto.PhraseRegResponseDto;
import com.example.diary.domain.recommend.service.RecommendService;
import com.example.diary.global.common.dto.ResponseDto;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RecommendController {

    private final RecommendService recommendService;

    @PostMapping("/v1/recommend/reg/excel")
    public ResponseDto<PhraseRegResponseDto> readPhraseExcel(@RequestParam("file") MultipartFile file)
            throws IOException {
        return new ResponseDto<>(recommendService.registerPhraseByExcel(file), HttpStatus.OK);
    }

    @PostMapping("/v1/recommend/reg/phrase")
    public ResponseDto<PhraseRegResponseDto> registerPhraseList(@RequestBody PhraseRegRequestDto phraseRegRequestDto) {
        return new ResponseDto<>(recommendService.registerPhrase(phraseRegRequestDto), HttpStatus.OK);
    }
}
