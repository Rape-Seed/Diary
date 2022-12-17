package com.example.diary.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
public class DiaryResponse {

    private Long diaryId;

    private String memberName;

    private String content;

    private String emotion;

    private String teamName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Builder
    public DiaryResponse(Long diaryId, String memberName, String content, String emotion, String teamName,
                         LocalDate date) {
        this.diaryId = diaryId;
        this.memberName = memberName;
        this.content = content;
        this.emotion = emotion;
        this.teamName = teamName;
        this.date = date;
    }
}
