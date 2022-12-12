package com.example.diary.domain.relation.repository;

import com.example.diary.domain.relation.entity.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRelationRepository {

    Page<Relation> findRelationFromType(String relationType, Pageable pageable);
}
