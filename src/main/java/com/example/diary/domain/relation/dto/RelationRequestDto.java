package com.example.diary.domain.relation.dto;

import lombok.Getter;

@Getter
public class RelationRequestDto {
    private String code;

    public RelationRequestDto() {
    }

    public RelationRequestDto(String code) {
        this.code = code;
    }
}
