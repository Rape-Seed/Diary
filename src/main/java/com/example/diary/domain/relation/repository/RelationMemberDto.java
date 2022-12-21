package com.example.diary.domain.relation.repository;


public class RelationMemberDto {

    private Long friendId;

    private String friendName;

    private String friendProfileImage;

    public RelationMemberDto() {
    }

    public RelationMemberDto(Long friendId, String friendName, String friendProfileImage) {
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendProfileImage = friendProfileImage;
    }

    public Long getFriendId() {
        return friendId;
    }

    public String getFriendName() {
        return friendName;
    }

    public String getFriendProfileImage() {
        return friendProfileImage;
    }

    @Override
    public String toString() {
        return "RelationMemberDto{" +
                "friendId=" + friendId +
                ", friendName='" + friendName + '\'' +
                ", friendProfileImage='" + friendProfileImage + '\'' +
                '}';
    }
}
