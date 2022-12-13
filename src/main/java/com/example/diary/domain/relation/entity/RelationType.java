package com.example.diary.domain.relation.entity;

public enum RelationType {
    ACCEPT("수락"), WAITING("수락 대기중"), APPLY("신청");

    private final String status;

    RelationType(String status) {
        this.status = status;
    }
}
