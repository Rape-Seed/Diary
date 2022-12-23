package com.example.diary.domain.relation.repository;

import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.entity.Relation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRelationRepository {

    Page<RelationMemberDto> findRelationFromType(Long memberId,
                                                 RelationSearchCondition condition,
                                                 Pageable pageable);

    Relation findRelationByDoubleId(Long memberId, Long friendId);
}
