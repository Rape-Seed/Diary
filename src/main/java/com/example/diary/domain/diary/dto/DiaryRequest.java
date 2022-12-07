package com.example.diary.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class DiaryRequest {

    private String content;

    private String emotion;

    private Long teamId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    private LocalDateTime currentTime;
}
