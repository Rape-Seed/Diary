package com.example.diary.domain.member.dto;

import lombok.Builder;

@Builder
public class MyInfoRequestDto {
    private String name;
    private String birthday;
    private String profileImage;

    public MyInfoRequestDto() {
    }

    public MyInfoRequestDto(String name, String birthday, String profileImage) {
        this.name = name;
        this.birthday = birthday;
        this.profileImage = profileImage;
    }

    public String getName() {
        return name;
    }

    public String getBirthday() {
        return birthday;
    }

    public String getProfileImage() {
        return profileImage;
    }
}
