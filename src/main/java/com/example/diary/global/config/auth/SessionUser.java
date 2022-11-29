package com.example.diary.global.config.auth;

import com.example.diary.domain.member.entity.Member;
import java.io.Serializable;

public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(Member member) {
        this.name = member.getName();
        this.email = member.getEmail();
    }
}
