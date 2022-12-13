package com.example.diary.domain.team.dto;

import com.example.diary.domain.team.entity.AcceptStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import lombok.Data;

@Data
public class DiaryTeamResponse {

    private Long id;

    private String name;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    private List<Member> members;

    public DiaryTeamResponse(Long id, String name, LocalDate startDate, LocalDate endDate,
                             List<Member> members) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.members = members;
    }

    @Data
    public static class Member {

        private Long id;

        private String name;

        private AcceptStatus acceptStatus;

        public Member(Long id, String name, AcceptStatus acceptStatus) {
            this.id = id;
            this.name = name;
            this.acceptStatus = acceptStatus;
        }
    }
}
