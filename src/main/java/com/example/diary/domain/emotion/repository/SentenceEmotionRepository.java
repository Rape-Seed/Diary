package com.example.diary.domain.emotion.repository;

import com.example.diary.domain.emotion.entity.SentenceEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentenceEmotionRepository extends JpaRepository<SentenceEmotion, Long> {
}
