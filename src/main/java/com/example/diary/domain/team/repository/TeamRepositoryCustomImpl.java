package com.example.diary.domain.team.repository;

import static com.example.diary.domain.team.entity.QTeam.team;
import static com.example.diary.domain.team.entity.QTeamMember.teamMember;
import static com.querydsl.core.group.GroupBy.groupBy;
import static com.querydsl.core.group.GroupBy.list;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.dto.DiaryTeamRequest;
import com.example.diary.domain.team.dto.DiaryTeamResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class TeamRepositoryCustomImpl implements TeamRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    private BooleanExpression teamMemberIdEq(Long memberId) {
        return memberId != null ? teamMember.member.id.eq(memberId) : null;
    }

    private BooleanExpression dateBetween(LocalDate date) {
        return date != null ? team.startDate.goe(date).and(team.endDate.loe(date)) : null;
    }

    public List<DiaryTeamResponse> findTeamsByMemberAndDate(Member selectMember,
                                                            DiaryTeamRequest diaryTeamRequest) {
        return queryFactory
                .from(team)
                .innerJoin(teamMember)
                .on(team.id.eq(teamMember.team.id))
                .where(teamMemberIdEq(selectMember.getId()),
                        dateBetween(diaryTeamRequest.getDate()))
                .transform(
                        groupBy(team.id)
                                .list(
                                        Projections.fields(
                                                DiaryTeamResponse.class,
                                                team.id,
                                                team.name,
                                                list(
                                                        Projections.fields(
                                                                DiaryTeamResponse.Member.class,
                                                                teamMember.member.id,
                                                                teamMember.member.name,
                                                                teamMember.acceptStatus
                                                        )
                                                ).as("member")
                                        )
                                )
                );
    }
}
