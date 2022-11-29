package com.example.diary.domain.recommend.entity;

import com.example.diary.domain.emotion.entity.Emotion;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Phrase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phrase_id")
    private Long id;

    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Emotion emotion;
}
