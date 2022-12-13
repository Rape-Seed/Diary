package com.example.diary.domain.relation.dto;

import com.example.diary.domain.relation.entity.Relation;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RelationApplyResponseDto {
    private Long memberId;
    private Long friendId;
    private String status;

    public RelationApplyResponseDto(Relation relation) {
        this.memberId = relation.getMember().getId();
        this.friendId = relation.getFriend().getId();
        this.status = relation.getRelationType().toString();
    }

    public RelationApplyResponseDto(Long memberId, Long friendId, String status) {
        this.memberId = memberId;
        this.friendId = friendId;
        this.status = status;
    }
}
