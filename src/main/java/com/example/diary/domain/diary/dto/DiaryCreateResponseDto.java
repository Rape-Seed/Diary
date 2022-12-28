package com.example.diary.domain.diary.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DiaryCreateResponseDto {

    private List<DiaryDto> diaries;
}
