package com.example.diary.domain.diary.entity;

import com.example.diary.domain.diary.dto.DiaryUpdateRequest;
import com.example.diary.domain.emotion.entity.DiaryEmotion;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;

@Builder
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

    @OneToOne
    @JoinColumn(name = "diary_emotion_id")
    private DiaryEmotion diaryEmotion;

    private LocalDate date;

    public Diary() {
    }

    public Diary(Long id, Member member, String content, Emotion emotion, Team team, LocalDate date) {
        this.id = id;
        this.member = member;
        this.content = content;
        this.emotion = emotion;
        this.team = team;
        this.date = date;
    }

    public void updateDiary(DiaryUpdateRequest diaryUpdateRequest) {
        this.content = diaryUpdateRequest.getContent();
    }
}
