package com.example.diary.domain.relation.repository;

import static com.example.diary.domain.member.entity.QMember.member;
import static com.example.diary.domain.relation.entity.QRelation.relation;

import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.entity.RelationType;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

@Slf4j
@Repository
@RequiredArgsConstructor
public class RelationRepositoryImpl implements CustomRelationRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<RelationMemberDto> findRelationFromType(Long memberId,
                                                        RelationSearchCondition condition,
                                                        Pageable pageable) {

        List<RelationMemberDto> content = getContentByCondition(memberId, condition, pageable);

        JPAQuery<Long> countQuery = getCountQueryByCondition(memberId, condition);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    private List<RelationMemberDto> getContentByCondition(Long memberId,
                                                          RelationSearchCondition condition,
                                                          Pageable pageable) {

        return queryFactory.select(Projections.constructor(RelationMemberDto.class,
                        relation.friend.id.as("friendId"),
                        relation.friend.name.as("friendName"),
                        relation.friend.profileImage.as("friendProfileImage")
                ))
                .from(relation)
                .leftJoin(relation.friend, member)
                .where(
                        memberIdEq(memberId),
                        relationTypeEq(condition.getStatus()),
                        nameEq(condition.getName()),
                        emailEq(condition.getEmail())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
    }

    private JPAQuery<Long> getCountQueryByCondition(Long memberId,
                                                    RelationSearchCondition condition) {
        return queryFactory
                .select(relation.count())
                .from(relation)
                .leftJoin(relation.friend, member)
                .where(
                        memberIdEq(memberId),
                        relationTypeEq(condition.getStatus()),
                        nameEq(condition.getName()),
                        emailEq(condition.getEmail())
                );
    }

    private BooleanExpression memberIdEq(Long memberId) {
        return memberId != null ? relation.member.id.eq(memberId) : null;
    }

    private BooleanExpression relationTypeEq(RelationType relationType) {
        return ObjectUtils.isEmpty(relationType) ? null : relation.relationType.eq(relationType);
    }

    private BooleanExpression nameEq(String name) {
        return StringUtils.hasText(name) ? relation.member.name.eq(name) : null;
    }

    private BooleanExpression emailEq(String email) {
        return StringUtils.hasText(email) ? relation.member.email.eq(email) : null;
    }
}
