package com.example.diary.domain.relation.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.dto.RelationAcceptRequestDto;
import com.example.diary.domain.relation.dto.RelationPagingDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationResponseDto;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.global.common.dto.ResponseDto;
import org.springframework.data.domain.Pageable;

public interface RelationService {

    RelationResponseDto enterIntoRelation(Member member, RelationRequestDto relationRequestDto);

    RelationPagingDto getRelationsByStatus(Member member,
                                           RelationSearchCondition condition,
                                           Pageable pageable);

    ResponseDto<RelationResponseDto> acceptRelation(Member member, RelationAcceptRequestDto dto);
}
