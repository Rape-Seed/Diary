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

@Getter
@Entity
public class Team extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "team_id")
    private Long id;

    private String code;

    @Builder.Default
    @OneToMany(mappedBy = "team")
    private List<TeamMember> teamMembers = new ArrayList<>();

    private LocalDate startDate;

    private LocalDate endDate;

    public Team() {
    }

    @Builder
    public Team(String code, LocalDate startDate, LocalDate endDate) {
        this.code = code;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
