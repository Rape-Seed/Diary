package com.example.diary.domain.diary.entity;

import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.emotion.entity.Emotion;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.global.common.BaseEntity;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Getter
@Entity
public class Diary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Emotion emotion;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    private LocalDate date;

    public Diary() {
    }

    @Builder
    public Diary(Member member, String content, Team team, LocalDate date) {
        this.member = member;
        this.content = content;
        this.team = team;
        this.date = date;
    }

    @Builder
    public Diary(Member member, String content, Emotion emotion, Team team, LocalDate date) {
        this.member = member;
        this.content = content;
        this.emotion = emotion;
        this.team = team;
        this.date = date;
    }

    public void updatePersonal(DiaryUpdateRequest diaryUpdateRequest) {
        this.content = diaryUpdateRequest.getContent();
    }
}
