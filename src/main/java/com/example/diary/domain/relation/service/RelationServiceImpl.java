package com.example.diary.domain.relation.service;

import static com.example.diary.domain.relation.entity.RelationType.APPLY;
import static com.example.diary.domain.relation.entity.RelationType.WAITING;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.dto.RelationApplyResponseDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.repository.CustomRelationRepository;
import com.example.diary.domain.relation.repository.RelationRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final CustomRelationRepository customRelationRepository;

    @Override
    @Transactional
    public RelationApplyResponseDto enterIntoRelation(Member member, RelationRequestDto relationRequestDto) {
        Member friend = memberRepository.findByCode(relationRequestDto.getCode());
        if (friend == null) {
            throw new MemberNotFoundException("존재하지않는 사용자입니다.");
        }

        Relation relationApply = relationRepository.save(new Relation(member, friend, APPLY));
        relationRepository.save(new Relation(friend, member, WAITING));

        return new RelationApplyResponseDto(relationApply);
    }
}
