package com.example.diary.domain.relation.dto;

public class RelationAcceptRequestDto {
    private Long friendId;

    public RelationAcceptRequestDto() {
    }

    public RelationAcceptRequestDto(Long friendId) {
        this.friendId = friendId;
    }

    public Long getFriendId() {
        return friendId;
    }
}
