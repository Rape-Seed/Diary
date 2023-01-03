package com.example.diary.domain.emotion.repository;


import com.example.diary.domain.emotion.entity.DiaryEmotion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiaryEmotionRepository extends JpaRepository<DiaryEmotion, Long> {
}
