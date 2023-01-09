package com.example.diary.domain.recommend.dto;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhraseRegRequestDto {
    private List<RegPhrase> phrases;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RegPhrase {
        private String id;
        private String content;
    }
}
