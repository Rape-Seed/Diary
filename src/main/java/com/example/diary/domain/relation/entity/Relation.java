package com.example.diary.domain.relation.entity;

import static com.example.diary.domain.relation.entity.RelationType.ACCEPT;

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

    public Relation() {
    }

    public Relation(Member member, Member friend, RelationType relationType) {
        setMember(member);
        this.friend = friend;
        this.relationType = relationType;
    }

    public void acceptRelation() {
        this.relationType = ACCEPT;
    }

    public void setMember(Member member) {
        if (this.member != null) {
            this.member.getRelations().remove(this);
        }
        this.member = member;
        member.getRelations().add(this);
    }

    public void setFriend(Member friend) {
        if (this.friend != null) {
            this.friend.getRelations().remove(this);
        }
        this.friend = friend;
        friend.getRelations().add(this);
    }

    @Override
    public String toString() {
        return "Relation{" +
                "id=" + id +
                ", member=" + member.getEmail() +
                ", friend=" + friend.getEmail() +
                ", relationType=" + relationType +
                '}';
    }
}
