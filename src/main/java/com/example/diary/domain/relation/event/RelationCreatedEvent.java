package com.example.diary.domain.relation.event;

import com.example.diary.domain.member.entity.Member;
import lombok.Getter;

@Getter
public class RelationCreatedEvent {

    private Member friend;
    private Member member;

    public RelationCreatedEvent() {
    }

    public RelationCreatedEvent(Member friend, Member member) {
        this.friend = friend;
        this.member = member;
    }

    public Member getFriend() {
        return friend;
    }

    public Member getMember() {
        return member;
    }
}
