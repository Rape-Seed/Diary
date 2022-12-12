package com.example.diary.domain.relation.entity;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Getter
@Entity
public class Relation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "relation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member friend;

    @Enumerated(EnumType.STRING)
    private RelationType relationType;

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getRelations().remove(this);
        }
        this.member = member;
        member.getRelations().add(this);
    }
}

/*
TODO
1. 친구 조회(임시친구, 확정친구)
2. 친구 추가
3. 친구 검색
 */
