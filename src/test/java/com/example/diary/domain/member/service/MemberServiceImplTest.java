package com.example.diary.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.diary.domain.member.dto.InfoResponseDto;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.entity.RelationType;
import com.example.diary.domain.relation.repository.RelationRepository;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceImplTest {
    @Autowired
    EntityManager em;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
    @Autowired
    RelationRepository relationRepository;

    private Member savedMember1;
    private Member savedMember2;


    @BeforeEach
    public void before() {
        Member member1 = makeMember("홍길동", "gil@gmail.com", "1q2w3e4r123123", "2000-01-01");
        Member member2 = makeMember("이길자", "gi2@gmail.com", "1q2w3e4r21231233", "2000-01-01");
        Relation relation = new Relation(member1, member2, RelationType.ACCEPT);
        savedMember1 = memberRepository.save(member1);
        savedMember2 = memberRepository.save(member2);
        relationRepository.save(relation);
    }

    @AfterEach
    void after() {
        relationRepository.deleteAll();
        memberRepository.deleteAll();
    }

    private Member makeMember(String name, String email, String code, String birthday) {
        return Member.builder()
                .name(name)
                .email(email)
                .code(RandomUtils.make())
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .profileImage("test")
                .platform(PlatformType.GOOGLE)
                .role(Role.MEMBER)
                .build();
    }

    @Test
    @Transactional(readOnly = true)
    void getMyInfo() {
        Member member = memberRepository.findById(savedMember1.getId()).orElseThrow();

        InfoResponseDto myInfo = memberService.getMyInfo(member);

        assertThat(myInfo.getName()).isEqualTo(member.getName());
        assertThat(myInfo.getEmail()).isEqualTo(member.getEmail());
        assertThat(myInfo.getBirthday()).isEqualTo(String.valueOf(member.getBirthday()));
        assertThat(myInfo.getProfileImage()).isEqualTo(member.getProfileImage());
    }


    @Test
    void updateMember() {
        Member member = memberRepository.findById(savedMember1.getId()).orElseThrow();

        InfoResponseDto updatedMemberInfo = memberService.updateMyInfo(
                member, new MyInfoRequestDto("알랑까몰라", "2021-01-01", "test123"));

        em.flush();
        em.clear();

        Member updatedMember = memberRepository.findById(savedMember1.getId()).orElseThrow();

        assertThat(updatedMemberInfo.getName()).isEqualTo(updatedMember.getName());
        assertThat(updatedMemberInfo.getBirthday()).isEqualTo(String.valueOf(updatedMember.getBirthday()));
        assertThat(updatedMemberInfo.getEmail()).isEqualTo(updatedMember.getEmail());
        assertThat(updatedMemberInfo.getProfileImage()).isEqualTo(updatedMember.getProfileImage());
    }

    @Test
    void getFriendInfo() {
        Member member1 = memberRepository.findById(savedMember1.getId()).orElseThrow();
        Member member2 = memberRepository.findById(savedMember2.getId()).orElseThrow();

        InfoResponseDto friendInfo = memberService.getMemberInfo(member1, member2.getId());

        assertThat(friendInfo.getName()).isEqualTo(member2.getName());
        assertThat(friendInfo.getEmail()).isEqualTo(member2.getEmail());
        assertThat(friendInfo.getBirthday()).isEqualTo(String.valueOf(member2.getBirthday()));
        assertThat(friendInfo.getProfileImage()).isEqualTo(member2.getProfileImage());
    }

    @Test
    @Transactional
    void deleteMember() {
        Member member = memberRepository.findById(savedMember1.getId()).orElseThrow();
        memberService.withdrawMembership(member);

        assertThat(memberRepository.findByCode("1q2w3e4r123123")).isNull();
    }

}