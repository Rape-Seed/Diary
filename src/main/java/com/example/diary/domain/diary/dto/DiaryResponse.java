package com.example.diary.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponse {

    private Long diaryId;

    private String member;

    private String content;

    private String emotion;

    private String teamId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Builder
    public DiaryResponse(Long diaryId, String member, String content, String emotion, String teamId,
                         LocalDate date) {
        this.diaryId = diaryId;
        this.member = member;
        this.content = content;
        this.emotion = emotion;
        this.teamId = teamId;
        this.date = date;
    }
}
