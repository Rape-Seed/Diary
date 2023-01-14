package com.example.diary.domain.emotion.entity;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.global.common.BaseEntity;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;

@Entity
@Getter
@Builder
public class DiaryEmotion extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diary_emotion_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
    private String content;
    private String sentiment;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "neutral", column = @Column(name = "total_neutral")),
            @AttributeOverride(name = "positive", column = @Column(name = "total_positive")),
            @AttributeOverride(name = "negative", column = @Column(name = "total_negative"))
    })
    private Analysis analysis = new Analysis();
    @Builder.Default
    @OneToMany(mappedBy = "diaryEmotion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SentenceEmotion> sentenceEmotions = new ArrayList<>();


    public DiaryEmotion() {
    }

    public DiaryEmotion(Long id, Diary diary, String content, String sentiment, Analysis analysis,
                        List<SentenceEmotion> sentenceEmotions) {
        this.id = id;
        this.diary = diary;
        this.content = content;
        this.sentiment = sentiment;
        this.analysis = analysis;
        this.sentenceEmotions = sentenceEmotions;
    }

    //    public DiaryEmotion(Long id, String diary, String sentiment, Analysis analysis,
//                        List<SentenceEmotion> sentenceEmotions) {
//        this.id = id;
//        this.diary = diary;
//        this.sentiment = sentiment;
//        this.analysis = analysis;
//        this.sentenceEmotions = sentenceEmotions;
//    }
}
