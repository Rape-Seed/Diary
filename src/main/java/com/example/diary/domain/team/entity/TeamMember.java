package com.example.diary.domain.team.entity;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class TeamMember extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_member_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Enumerated(EnumType.STRING)
    private AcceptStatus acceptStatus;

    public TeamMember() {
    }

    @Builder
    public TeamMember(Team team, Member member, AcceptStatus acceptStatus) {
        this.team = team;
        this.member = member;
        this.acceptStatus = acceptStatus;
    }
}
