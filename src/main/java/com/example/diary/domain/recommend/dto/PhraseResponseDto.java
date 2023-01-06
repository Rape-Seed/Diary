package com.example.diary.domain.recommend.dto;

public class PhraseResponseDto {
    private final String phrase;

    public PhraseResponseDto(String phrase) {
        this.phrase = phrase;
    }

    public String getPhrase() {
        return phrase;
    }
}
