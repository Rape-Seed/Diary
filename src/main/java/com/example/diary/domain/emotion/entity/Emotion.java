package com.example.diary.domain.emotion.entity;

import com.example.diary.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Entity
public class Emotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "emotion_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private EmotionType content;

    public Emotion() {
    }

    public Emotion(Long id, EmotionType content) {
        this.id = id;
        this.content = content;
    }
}
