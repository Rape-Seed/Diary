package com.example.diary.domain.recommend.repository;

import com.example.diary.domain.recommend.entity.Phrase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhraseRepository extends JpaRepository<Phrase, Long>, PhraseRepositoryCustom {
    Phrase findByContent(String content);
}
