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

    public static DiaryDto ofPersonal(Diary diary) {
        return DiaryDto.builder()
                .diaryId(diary.getId())
                .memberName(diary.getMember().getName())
                .content(diary.getContent())
                .emotion(diary.getEmotion().getContent().getMessage())
                .date(diary.getDate())
                .build();
    }

    public static DiaryDto ofShared(Diary diary) {
        return DiaryDto.builder()
                .diaryId(diary.getId())
                .memberName(diary.getMember().getName())
                .teamName(diary.getTeam().getName())
                .content(diary.getContent())
                .emotion(diary.getEmotion().getContent().getMessage())
                .date(diary.getDate())
                .build();
    }
}
