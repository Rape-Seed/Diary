package com.example.diary.domain.diary.repository;

import static com.example.diary.domain.diary.entity.QDiary.diary;
import static com.example.diary.domain.member.entity.QMember.member;
import static com.example.diary.domain.team.entity.QTeam.team;
import static com.example.diary.domain.team.entity.QTeamMember.teamMember;

import com.example.diary.domain.diary.entity.Diary;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class DiaryRepositoryCustomImpl implements DiaryRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Diary findDiaryByDiaryId(Long diaryId) {
        return queryFactory.selectFrom(diary)
                .join(diary.member, member).fetchJoin()
                .join(diary.team, team).fetchJoin()
                .join(team.teamMembers, teamMember).fetchJoin()
                .where(diary.id.eq(diaryId))
                .fetchOne();
    }
}
