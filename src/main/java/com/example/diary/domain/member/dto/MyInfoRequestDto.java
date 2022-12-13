package com.example.diary.domain.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MyInfoRequestDto {
    private String name;
    private String birthday;
    private String profileImage;
}
