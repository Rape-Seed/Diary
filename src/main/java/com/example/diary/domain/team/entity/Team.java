package com.example.diary.domain.team.entity;

import com.example.diary.global.common.BaseEntity;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String name;

    private String code;

    @Builder.Default
    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    private LocalDate startDate;

    private LocalDate endDate;

    public Team() {
    }

    public Team(Long id, String name, String code, List<TeamMember> teamMembers, LocalDate startDate,
                LocalDate endDate) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.teamMembers = teamMembers;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
