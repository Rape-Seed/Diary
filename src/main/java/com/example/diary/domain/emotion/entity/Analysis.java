package com.example.diary.domain.emotion.entity;

import com.example.diary.domain.emotion.dto.EmotionAnalyzeDto.Confidence;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class Analysis {
    private Double neutral;
    private Double positive;
    private Double negative;

    public Analysis() {
    }

    public Analysis(Confidence confidence) {
        this(confidence.getNeutral(), confidence.getPositive(), confidence.getNegative());
    }

    public Analysis(Double neutral, Double positive, Double negative) {
        this.neutral = neutral;
        this.positive = positive;
        this.negative = negative;
    }
}
