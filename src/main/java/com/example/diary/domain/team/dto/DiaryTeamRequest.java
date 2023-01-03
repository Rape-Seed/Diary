package com.example.diary.domain.team.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class DiaryTeamRequest {

    private LocalDate date;
}
