package com.example.diary.domain.relation.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class RelationRequestDto {
    private String code;

    public String getCode() {
        return code;
    }
}
