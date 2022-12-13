package com.example.diary.domain.team.repository;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.Team;
import com.example.diary.domain.team.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {

    TeamMember findTeamMemberByTeamAndMember(Team team, Member member);
}
