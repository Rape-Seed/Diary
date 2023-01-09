package com.example.diary.domain.recommend.dto;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Builder
@ToString
public class PhraseRegResponseDto {
    private List<String> savedSuccess;
    private List<Failure> savedFailure;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    public static class Failure {
        private String phrase;
        private String reason;
    }

    public PhraseRegResponseDto() {
        this.savedSuccess = new ArrayList<>();
        this.savedFailure = new ArrayList<>();
    }

    public void addSuccess(String content) {
        this.savedSuccess.add(content);
    }

    public void addFailure(String content, String reason) {
        this.savedFailure.add(new Failure(content, reason));
    }


}
