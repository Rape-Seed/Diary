package com.example.diary.domain.relation.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.dto.RelationApplyResponseDto;
import com.example.diary.domain.relation.dto.RelationPagingDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import org.springframework.data.domain.Pageable;

public interface RelationService {

    RelationApplyResponseDto enterIntoRelation(Member member, RelationRequestDto relationRequestDto);

    RelationPagingDto getRelationsByStatus(Member member,
                                           RelationSearchCondition condition,
                                           Pageable pageable);
}
