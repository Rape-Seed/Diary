package com.example.diary.domain.team_member.repository;

import com.example.diary.domain.team_member.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
}
