package com.example.diary.domain.member.dto;

import com.example.diary.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class MyInfoResponseDto {

    private String name;
    private String email;
    private String birthday;
    private String profileImage;

    public MyInfoResponseDto(Member member) {
        this(member.getName(), member.getEmail(), String.valueOf(member.getBirthday()), member.getProfileImage());
    }

    public MyInfoResponseDto(String name, String email, String birthday, String profileImage) {
        this.name = name;
        this.email = email;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }
}
