package com.example.diary.domain.member.entity;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.group.entity.Group;
import com.example.diary.domain.relation.entity.Relation;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String code;

    private LocalDate birthday;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Relation> relations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Group> groups = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PlatForm platForm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public String getRoleKey() {
        return this.role.getKey();
    }

    public Member update(String name) {
        this.name = name;

        return this;
    }
}
