package com.example.diary.domain.relation.dto;

import com.example.diary.domain.relation.entity.RelationType;

public class RelationSearchCondition {
    private String name;
    private String email;

    private String status;

    public RelationSearchCondition() {
    }

    public RelationSearchCondition(String status) {
        this(null, null, status);
    }

    public RelationSearchCondition(String name, String email, String status) {
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public RelationType getStatus() {
        return RelationType.valueOf(status.toUpperCase());
    }
}
