package com.example.diary.domain.relation.service;

import static com.example.diary.domain.notification.entity.NotificationType.RELATION_ACCEPT;
import static com.example.diary.domain.notification.entity.NotificationType.RELATION_APPLY;
import static com.example.diary.domain.relation.entity.RelationType.ACCEPT;
import static com.example.diary.domain.relation.entity.RelationType.APPLY;
import static com.example.diary.domain.relation.entity.RelationType.WAITING;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.notification.entity.Notification;
import com.example.diary.domain.notification.repository.NotificationRepository;
import com.example.diary.domain.relation.dto.RelationDecideRequestDto;
import com.example.diary.domain.relation.dto.RelationPagingDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationResponseDto;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.repository.RelationMemberDto;
import com.example.diary.domain.relation.repository.RelationRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exception.RelationAlreadyExistException;
import com.example.diary.global.advice.exception.RelationAlreadyFormedException;
import com.example.diary.global.advice.exception.RelationNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RelationServiceImpl implements RelationService {

    private final MemberRepository memberRepository;
    private final RelationRepository relationRepository;
    private final NotificationRepository notificationRepository;

    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public RelationResponseDto enterIntoRelation(Member member, RelationRequestDto relationRequestDto) {
        Member friend = memberRepository.findByCode(relationRequestDto.getCode());
        checkExistFriend(friend);
        checkAlreadyFriend(member, friend);

        Relation relationApply = relationRepository.save(new Relation(member, friend, APPLY));
        relationRepository.save(new Relation(friend, member, WAITING));
        notificationRepository.save(new Notification().makeNotificationByMakeRelation(member, friend, RELATION_APPLY));
//        eventPublisher.publishEvent(new RelationCreatedEvent(friend, member));
        return new RelationResponseDto(relationApply);
    }

    private void checkAlreadyFriend(Member member, Member friend) {
        if (relationRepository.findRelationByDoubleId(member.getId(), friend.getId()) != null) {
            throw new RelationAlreadyExistException();
        }
    }

    private static void checkExistFriend(Member friend) {
        if (friend == null) {
            throw new MemberNotFoundException("존재하지않는 사용자입니다.");
        }
    }

    @Override
    @Transactional
    public RelationResponseDto acceptRelation(Member member, RelationDecideRequestDto dto) {
        Member friend = memberRepository.findById(dto.getFriendId())
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자 입니다."));

        Relation relationByMe = relationRepository.findRelationByDoubleId(member.getId(), friend.getId());
        Relation relationByFriend = relationRepository.findRelationByDoubleId(friend.getId(), member.getId());

        checkRelation(relationByMe, relationByFriend);
        checkAlreadyFriend(relationByMe, relationByFriend);

        relationByMe.acceptRelation();
        relationByFriend.acceptRelation();
        notificationRepository.save(
                new Notification().makeNotificationByAcceptRelation(member, friend, RELATION_ACCEPT));
        return new RelationResponseDto(relationByMe);
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

    private void checkAlreadyFriend(Relation relationByMe, Relation relationByFriend) {
        if (relationByMe.getRelationType() == ACCEPT && relationByFriend.getRelationType() != ACCEPT) {
            relationByFriend.acceptRelation();
            return;
        }
        if (relationByFriend.getRelationType() == ACCEPT && relationByMe.getRelationType() != ACCEPT) {
            relationRepository.delete(relationByMe);
            relationRepository.delete(relationByFriend);
            throw new IllegalArgumentException("알 수 없는 에러입니다.");
        }

        if (relationByMe.getRelationType() == ACCEPT && relationByFriend.getRelationType() == ACCEPT) {
            throw new RelationAlreadyFormedException();
        }
    }

    @Override
    public void rejectRelation(Member member, Long friendId) {
        Member friend = memberRepository.findById(friendId)
                .orElseThrow(() -> new MemberNotFoundException("존재하지 않는 사용자 입니다."));

        member.getRelations()
                .remove(relationRepository.findRelationByDoubleId(member.getId(), friend.getId()));
        friend.getRelations()
                .remove(relationRepository.findRelationByDoubleId(friend.getId(), member.getId()));

    }

    @Override
    public RelationPagingDto getRelationsByStatus(Member member,
                                                  RelationSearchCondition condition,
                                                  Pageable pageable) {
        Page<RelationMemberDto> result = relationRepository.
                findRelationFromType(
                        member.getId(), condition, pageable
                );

        return new RelationPagingDto(result);
    }


}
