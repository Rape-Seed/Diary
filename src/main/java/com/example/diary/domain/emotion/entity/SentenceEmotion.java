package com.example.diary.domain.emotion.entity;

import com.example.diary.global.common.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class SentenceEmotion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sentence_emotion_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private DiaryEmotion diaryEmotion;
    private String content;
    @Column(name = "eOffset")
    private Integer offset;
    private Integer length;
    private String sentiment;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "neutral", column = @Column(name = "sentence_neutral")),
            @AttributeOverride(name = "positive", column = @Column(name = "sentence_positive")),
            @AttributeOverride(name = "negative", column = @Column(name = "sentence_negative"))
    })
    private Analysis analysis;

    public SentenceEmotion() {
    }

    public SentenceEmotion(Long id, DiaryEmotion diaryEmotion, String content, Integer offset, Integer length,
                           String sentiment, Analysis analysis) {
        this.id = id;
        this.diaryEmotion = diaryEmotion;
        this.content = content;
        this.offset = offset;
        this.length = length;
        this.sentiment = sentiment;
        this.analysis = analysis;
    }

    public void setDiaryEmotion(DiaryEmotion diaryEmotion) {
        if (this.diaryEmotion != null) {
            this.diaryEmotion.getSentenceEmotions().remove(this);
        }
        this.diaryEmotion = diaryEmotion;
        diaryEmotion.getSentenceEmotions().add(this);
    }

}
