package com.example.diary.domain.recommend.entity;

import com.example.diary.domain.recommend.dto.ExcelData;
import com.example.diary.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Phrase extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phrase_id")
    private Long id;

    private String content;

    public Phrase(String content) {
        this.content = content;
    }

    public Phrase(ExcelData excelData) {
        this.content = excelData.getColumn2();
    }
}
