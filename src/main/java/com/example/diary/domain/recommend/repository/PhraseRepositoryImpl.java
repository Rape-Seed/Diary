package com.example.diary.domain.recommend.repository;

import static com.example.diary.domain.recommend.entity.QPhrase.phrase;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PhraseRepositoryImpl implements PhraseRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Long findLastIndexNumber() {
        return queryFactory.select(phrase.id)
                .from(phrase)
                .orderBy(phrase.id.desc())
                .limit(1)
                .fetchOne();
    }

}
