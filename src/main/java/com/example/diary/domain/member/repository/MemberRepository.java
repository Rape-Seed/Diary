package com.example.diary.domain.member.repository;

import com.example.diary.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Member findByEmail(String email);

    Member findByCode(String code);

    Boolean existsByCode(String code);

}
