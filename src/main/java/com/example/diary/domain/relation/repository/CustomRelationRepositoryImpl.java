package com.example.diary.domain.relation.repository;

import com.example.diary.domain.relation.entity.Relation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomRelationRepositoryImpl implements CustomRelationRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Relation> findRelationFromType(String relationType, Pageable pageable) {
        return null;
    }
}
