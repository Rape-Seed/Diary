package com.example.diary.domain.relation.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.relation.dto.RelationApplyResponseDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;

public interface RelationService {

    RelationApplyResponseDto enterIntoRelation(Member member, RelationRequestDto relationRequestDto);
}
