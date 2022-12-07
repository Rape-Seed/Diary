package com.example.diary.domain.member.entity;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.team_member.entity.TeamMember;
import com.example.diary.global.common.BaseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    private String email;

    private String code;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Relation> relations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();
}
