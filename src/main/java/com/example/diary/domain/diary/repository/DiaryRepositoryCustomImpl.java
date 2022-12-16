package com.example.diary.domain.diary.repository;

import static com.example.diary.domain.diary.entity.QDiary.diary;
import static com.example.diary.domain.member.entity.QMember.member;
import static com.example.diary.domain.team.entity.QTeam.team;

import com.example.diary.domain.diary.entity.Diary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Diary findDiaryByMemberIdAndTeamIdAndDate(Long memberId, Long teamId, LocalDate date) {
        return queryFactory.selectFrom(diary)
                .join(diary.member, member).fetchJoin()
                .join(diary.team, team).fetchJoin()
                .where(diary.team.id.eq(teamId), diary.member.id.eq(memberId), diary.date.eq(date))
                .fetchOne();
    }
}
