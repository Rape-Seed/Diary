package com.example.diary.domain.team.dto;

import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TeamInviteResponse {

    private Long teamId;

    private List<MemberDto> friends;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    public TeamInviteResponse(List<TeamMember> members, Team team) {
        this.friends = members.stream()
                .map(m -> new MemberDto(m.getMember().getProfileImage(), m.getMember().getName()))
                .collect(Collectors.toList());
        this.startDate = team.getStartDate();
        this.endDate = team.getEndDate();
    }

    @Builder
    @Data
    public static class MemberDto {

        private String profileImage;

        private String name;

        public MemberDto(String profileImage, String name) {
            this.profileImage = profileImage;
            this.name = name;
        }
    }
}
