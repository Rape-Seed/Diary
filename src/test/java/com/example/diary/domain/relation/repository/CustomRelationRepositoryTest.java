package com.example.diary.domain.relation.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.entity.RelationType;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CustomRelationRepositoryTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RelationRepository relationRepository;

    private Member savedMember1;

    @BeforeEach
    void setup() {
        Member member1 = makeMember("홍길동", "gil@gmail.com", "1q2w3e4r", "2000-01-01");
        savedMember1 = memberRepository.save(member1);

        for (int i = 0; i < 50; i++) {
            Member newMember = makeMember("테스트" + i, "qwer" + i + "@gmail.com", "qwer" + i, "2002-01-01");
            Member savedMember2 = memberRepository.save(newMember);
            relationRepository.save(new Relation(savedMember1, savedMember2, RelationType.APPLY));
            relationRepository.save(new Relation(savedMember2, savedMember1, RelationType.WAITING));
        }
    }

    private Member makeMember(String name, String email, String code, String birthday) {
        return Member.builder()
                .name(name)
                .email(email)
                .code(RandomUtils.make())
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .profileImage("")
                .platform(PlatformType.GOOGLE)
                .role(Role.MEMBER)
                .build();
    }

    @Test
    void findRelationByDoubleId() {
        Member member = memberRepository.findById(savedMember1.getId()).orElseThrow();
        Member friend = memberRepository.findByEmail("qwer2@gmail.com");

        Relation result = relationRepository.findRelationByDoubleId(member.getId(), friend.getId());

        assertThat(result.getMember()).isEqualTo(member);
        assertThat(result.getFriend()).isEqualTo(friend);
    }

    @Test
    void checkFindFriendQuery() {
        Member member = memberRepository.findById(savedMember1.getId()).orElseThrow();
        PageRequest pageRequest = PageRequest.of(0, 20);
        System.out.println(member.getId());

        Page<RelationMemberDto> result = relationRepository.
                findRelationFromType(member.getId(), new RelationSearchCondition("apply"), pageRequest);

        assertThat(result.getTotalElements()).isEqualTo(50);
        assertThat(result.getTotalPages()).isEqualTo(3);
        int i = 0;
        for (RelationMemberDto relationMemberDto : result.getContent()) {
            assertThat(relationMemberDto.getFriendName()).isEqualTo("테스트" + i);
            i++;
        }
    }


}