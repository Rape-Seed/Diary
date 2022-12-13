package com.example.diary.domain.relation.dto;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.entity.Relation;
import lombok.Getter;

@Getter
public class RelationBriefInfo {
    private String name;
    private String email;
    private String code;

    public RelationBriefInfo() {
    }

    public RelationBriefInfo(String name, String email, String code) {
        this.name = name;
        this.email = email;
        this.code = code;
    }

    public RelationBriefInfo(Relation relation) {
        Member friend = relation.getFriend();
        this.name = friend.getName();
        this.email = friend.getEmail();
        this.code = friend.getCode();
    }
}
