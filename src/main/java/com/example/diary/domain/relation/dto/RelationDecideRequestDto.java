package com.example.diary.domain.relation.dto;

public class RelationDecideRequestDto {
    private Long friendId;

    public RelationDecideRequestDto() {
    }

    public RelationDecideRequestDto(Long friendId) {
        this.friendId = friendId;
    }

    public Long getFriendId() {
        return friendId;
    }
}
