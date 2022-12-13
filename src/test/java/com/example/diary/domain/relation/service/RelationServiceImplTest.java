package com.example.diary.domain.relation.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.repository.RelationRepository;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RelationServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RelationRepository relationRepository;

    @BeforeEach
    public void before() {
        Member member1 = makeMember("홍길동", "gil@gmail.com", "1q2w3e4r", "2000-01-01");
        Member member2 = makeMember("이춘향", "chun@gmail.com", "qwerty", "2001-01-01");
        memberRepository.save(member1);
        memberRepository.save(member2);
    }

    private Member makeMember(String name, String email, String code, String birthday) {
        return Member.builder()
                .name(name)
                .email(email)
                .code(code)
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .profileImage("")
                .platform(PlatformType.GOOGLE)
                .role(Role.MEMBER)
                .build();
    }

}