package com.example.diary.domain.diary.dto;

import com.example.diary.domain.diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class DiaryDto {

    private Long diaryId;

    private String memberName;

    private String content;

    private String emotion;

    private String teamName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Builder
    public DiaryDto(Long diaryId, String memberName, String content, String emotion, String teamName,
                    LocalDate date) {
        this.diaryId = diaryId;
        this.memberName = memberName;
        this.content = content;
        this.emotion = emotion;
        this.teamName = teamName;
        this.date = date;
    }

    public DiaryDto(Diary diary) {
        this.diaryId = diary.getId();
        this.memberName = diary.getMember().getName();
        this.content = diary.getContent();
        this.teamName = diary.getTeam().getName();
        this.emotion = diary.getEmotion().getContent().toString();
        this.date = diary.getDate();
    }
}
