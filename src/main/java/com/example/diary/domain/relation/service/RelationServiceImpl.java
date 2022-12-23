package com.example.diary.domain.relation.service;

import static com.example.diary.domain.relation.entity.RelationType.APPLY;
import static com.example.diary.domain.relation.entity.RelationType.WAITING;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.dto.RelationAcceptRequestDto;
import com.example.diary.domain.relation.dto.RelationPagingDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationResponseDto;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.repository.CustomRelationRepository;
import com.example.diary.domain.relation.repository.RelationMemberDto;
import com.example.diary.domain.relation.repository.RelationRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exception.RelationAlreadyExistException;
import com.example.diary.global.advice.exception.RelationNotFoundException;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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
    public RelationResponseDto enterIntoRelation(Member member, RelationRequestDto relationRequestDto) {
        Member friend = memberRepository.findByCode(relationRequestDto.getCode());
        if (friend == null) {
            throw new MemberNotFoundException("존재하지않는 사용자입니다.");
        }
        if (customRelationRepository.findRelationByDoubleId(member.getId(), friend.getId()) != null) {
            throw new RelationAlreadyExistException();
        }

        Relation relationApply = relationRepository.save(new Relation(member, friend, APPLY));
        relationRepository.save(new Relation(friend, member, WAITING));

        return new RelationResponseDto(relationApply);
    }

    @Override
    @Transactional
    public ResponseDto<?> acceptRelation(Member member, RelationAcceptRequestDto dto) {
        Relation relationByMe = customRelationRepository.findRelationByDoubleId(member.getId(), dto.getFriendId());
        Relation relationByFriend = customRelationRepository.findRelationByDoubleId(dto.getFriendId(), member.getId());
        checkRelation(relationByMe, relationByFriend);

        relationByMe.acceptRelation();
        relationByFriend.acceptRelation();
        return new ResponseDto<>(new RelationResponseDto(relationByMe), HttpStatus.OK);
    }

    private void checkRelation(Relation relationByMe, Relation relationByFriend) {
        if (relationByMe == null && relationByFriend == null) {
            throw new RelationNotFoundException();
        }

        if (relationByMe == null) {
            relationRepository.delete(relationByFriend);
            throw new RelationNotFoundException();
        }

        if (relationByFriend == null) {
            relationRepository.delete(relationByMe);
            throw new RelationNotFoundException();
        }
    }


    @Override
    public RelationPagingDto getRelationsByStatus(Member member,
                                                  RelationSearchCondition condition,
                                                  Pageable pageable) {
        Page<RelationMemberDto> result = customRelationRepository.
                findRelationFromType(
                        member.getId(), condition, pageable
                );

        return new RelationPagingDto(result);
    }


}
