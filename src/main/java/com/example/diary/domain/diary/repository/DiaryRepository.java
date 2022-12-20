package com.example.diary.domain.diary.repository;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.team.entity.Team;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long>, DiaryRepositoryCustom {

    Diary findDiaryByTeamAndMemberAndDate(Team team, Member member, LocalDate date);
}
