package com.example.diary.domain.emotion.dto;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.emotion.entity.Analysis;
import com.example.diary.domain.emotion.entity.DiaryEmotion;
import com.example.diary.domain.emotion.entity.SentenceEmotion;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EmotionAnalyzeDto {
    private Documents document;
    private List<Sentences> sentences;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class Documents {
        private String sentiment;
        private Confidence confidence;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Confidence {
        private Double neutral;
        private Double positive;
        private Double negative;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Sentences {
        private String content;
        private int offset;
        private int length;
        private String sentiment;
        private Confidence confidence;
        private List<Highlight> highlights;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        public static class Highlight {
            private int offset;
            private int length;
        }
    }

    public DiaryEmotion toDiaryEmotion(String content, Diary diary) {
        return DiaryEmotion.builder()
                .diary(diary)
                .content(content)
                .sentiment(this.document.getSentiment())
                .analysis(new Analysis(this.document.getConfidence()))
                .build();
    }

    public SentenceEmotion toSentenceEmotion(Sentences sentence) {
        return SentenceEmotion.builder()
                .content(sentence.getContent())
                .offset(sentence.getOffset())
                .length(sentence.getLength())
                .sentiment(sentence.getSentiment())
                .analysis(new Analysis(sentence.getConfidence()))
                .build();

    }
}
