package com.example.diary.domain.member.dto;

import com.example.diary.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class InfoResponseDto {

    private String name;
    private String email;
    private String birthday;
    private String profileImage;

    public InfoResponseDto(Member member) {
        this(member.getName(), member.getEmail(), String.valueOf(member.getBirthday()), member.getProfileImage());
    }

    public InfoResponseDto(String name, String email, String birthday, String profileImage) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }
}
