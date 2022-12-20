package com.example.diary.domain.diary.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DiaryUpdateRequest {

    private String content;
}
