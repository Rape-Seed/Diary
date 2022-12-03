package com.example.diary.domain.team.repository;

import com.example.diary.domain.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
