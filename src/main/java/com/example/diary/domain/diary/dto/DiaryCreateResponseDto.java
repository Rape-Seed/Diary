package com.example.diary.domain.diary.dto;

import com.example.diary.domain.diary.entity.Diary;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiaryCreateResponseDto {

    private List<DiaryCreateDto> diaries;

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DiaryCreateDto {

        private Long diaryId;

        private String memberName;

        private String content;

        private String teamName;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
        private LocalDate date;

        public static DiaryCreateDto ofPersonal(Diary diary) {
            return DiaryCreateDto.builder()
                    .diaryId(diary.getId())
                    .memberName(diary.getMember().getName())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .build();
        }

        public static DiaryCreateDto ofShared(Diary diary) {
            return DiaryCreateDto.builder()
                    .diaryId(diary.getId())
                    .memberName(diary.getMember().getName())
                    .teamName(diary.getTeam().getName())
                    .content(diary.getContent())
                    .date(diary.getDate())
                    .build();
        }
    }
}
