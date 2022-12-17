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

@Builder
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

    public TeamMember(Long id, Team team, Member member, AcceptStatus acceptStatus) {
        this.id = id;
        this.team = team;
        this.member = member;
        this.acceptStatus = acceptStatus;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getTeamMembers().remove(this);
        }
        this.member = member;
        member.getTeamMembers().add(this);
    }

    public void setTeam(Team team) {
        if (this.team != null) {
            this.team.getTeamMembers().remove(this);
        }
        this.team = team;
        team.getTeamMembers().add(this);
    }

    public void updateAcceptStatus(AcceptStatus acceptStatus) {
        this.acceptStatus = acceptStatus;
    }
}
