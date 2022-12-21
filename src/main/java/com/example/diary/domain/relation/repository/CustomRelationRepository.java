package com.example.diary.domain.relation.repository;

import com.example.diary.domain.relation.dto.RelationSearchCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CustomRelationRepository {

    Page<RelationMemberDto> findRelationFromType(Long memberId,
                                                 RelationSearchCondition condition,
                                                 Pageable pageable);


}
