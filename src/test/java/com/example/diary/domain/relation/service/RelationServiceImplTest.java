package com.example.diary.domain.relation.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.dto.RelationAcceptRequestDto;
import com.example.diary.domain.relation.dto.RelationPagingDto;
import com.example.diary.domain.relation.dto.RelationRequestDto;
import com.example.diary.domain.relation.dto.RelationResponseDto;
import com.example.diary.domain.relation.dto.RelationSearchCondition;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.entity.RelationType;
import com.example.diary.domain.relation.repository.CustomRelationRepository;
import com.example.diary.domain.relation.repository.RelationMemberDto;
import com.example.diary.domain.relation.repository.RelationRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.advice.exception.RelationAlreadyExistException;
import com.example.diary.global.advice.exception.RelationAlreadyFormedException;
import com.example.diary.global.advice.exception.RelationNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class RelationServiceImplTest {

    @Autowired
    RelationService relationService;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RelationRepository relationRepository;

    @Autowired
    EntityManager entityManager;

    @Autowired
    CustomRelationRepository customRelationRepository;

    @BeforeEach
    public void before() {
        Member member1 = makeMember("홍길동", "gil@gmail.com", "1q2w3e4r", "2000-01-01");
        Member testMember = makeMember("test", "test" + "@gmail.com", "test", "2002-01-01");
        Member savedMember1 = memberRepository.save(member1);
        Member testMember1 = memberRepository.save(testMember);

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
                .code(code)
                .birthday(LocalDate.parse(birthday, DateTimeFormatter.ISO_DATE))
                .profileImage("")
                .platform(PlatformType.GOOGLE)
                .role(Role.MEMBER)
                .build();
    }

    @Test
    void enterIntoRelation() {
        Member member = memberRepository.findByEmail("qwer1@gmail.com");

        RelationResponseDto relationApplyResponseDto = relationService.enterIntoRelation(member,
                new RelationRequestDto("qwer10"));

        Member friend = memberRepository.findByEmail("qwer10@gmail.com");
        assertThat(relationApplyResponseDto.getMemberId()).isEqualTo(member.getId());
        assertThat(relationApplyResponseDto.getFriendId()).isEqualTo(friend.getId());
        assertThat(RelationType.valueOf(relationApplyResponseDto.getStatus())).isEqualTo(RelationType.APPLY);

        assertThat(friend.getRelations().size()).isEqualTo(2);
    }

    @Test
    void enterIntoRelationFailByMemberNotFound() {
        Member member = memberRepository.findByEmail("qwer1@gmail.com");
        assertThatThrownBy(
                () -> relationService.enterIntoRelation(member, new RelationRequestDto("qwerpoi"))
        ).isInstanceOf(MemberNotFoundException.class);
    }

    @Test
    void enterIntoRelationFailByRelationAlreadyExist() {
        Member member = memberRepository.findByEmail("gil@gmail.com");
        assertThatThrownBy(
                () -> relationService.enterIntoRelation(member, new RelationRequestDto("qwer2"))
        ).isInstanceOf(RelationAlreadyExistException.class);
    }

    @Test
    void getRelationsByStatus() {
        Member member = memberRepository.findByEmail("gil@gmail.com");
        PageRequest pageRequest = PageRequest.of(0, 10);
        RelationPagingDto result =
                relationService.getRelationsByStatus(member, new RelationSearchCondition("apply"), pageRequest);

        assertThat(result.getTotalPageCount()).isEqualTo(5);
        assertThat(result.getTotalElementCount()).isEqualTo(50);
        assertThat(result.getCurrentPageCount()).isEqualTo(0);
        assertThat(result.getCurrentPageElementCount()).isEqualTo(10);
    }

    @Test
    void acceptRelation() {
        Member member = memberRepository.findByEmail("gil@gmail.com");
        Member member1 = memberRepository.findByEmail("qwer2@gmail.com");
        Member member2 = memberRepository.findByEmail("qwer3@gmail.com");

        RelationAcceptRequestDto dto1 = new RelationAcceptRequestDto(member1.getId());
        RelationAcceptRequestDto dto2 = new RelationAcceptRequestDto(member2.getId());

        relationService.acceptRelation(member, dto1);
        relationService.acceptRelation(member, dto2);

        Relation result1 = customRelationRepository.findRelationByDoubleId(member.getId(), member1.getId());
        Relation result2 = customRelationRepository.findRelationByDoubleId(member.getId(), member2.getId());

        assertThat(result1.getRelationType()).isEqualTo(RelationType.ACCEPT);
        assertThat(result2.getRelationType()).isEqualTo(RelationType.ACCEPT);

        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<RelationMemberDto> acceptMember = customRelationRepository.findRelationFromType(member.getId(),
                new RelationSearchCondition("accept"),
                pageRequest);
        assertThat(acceptMember.getTotalElements()).isEqualTo(2);
    }

    @Test
    void acceptFailByRelationNotFound() {
        Member member = memberRepository.findByEmail("gil@gmail.com");
        Member member1 = memberRepository.findByEmail("test@gmail.com");

        assertThatThrownBy(
                () -> relationService.acceptRelation(member, new RelationAcceptRequestDto(member1.getId())))
                .isInstanceOf(RelationNotFoundException.class);
    }

    @Test
    void acceptFailByRelationNotFoundException() {
        Member member = memberRepository.findByEmail("gil@gmail.com");
        Member member1 = memberRepository.findByEmail("qwer2@gmail.com");

        relationService.acceptRelation(member, new RelationAcceptRequestDto(member1.getId()));

        assertThatThrownBy(
                () -> relationService.acceptRelation(member, new RelationAcceptRequestDto(member1.getId())))
                .isInstanceOf(RelationAlreadyFormedException.class);
    }

    @Test
    void acceptFailByIllegalArgumentException() {
        Member member = memberRepository.findByEmail("gil@gmail.com");
        Member member1 = memberRepository.findByEmail("qwer2@gmail.com");

        Relation me = customRelationRepository.findRelationByDoubleId(member.getId(), member1.getId());
        Relation friend = customRelationRepository.findRelationByDoubleId(member1.getId(), member.getId());
        friend.acceptRelation();

        assertThatThrownBy(
                () -> relationService.acceptRelation(member, new RelationAcceptRequestDto(member1.getId())))
                .isInstanceOf(IllegalArgumentException.class);
    }

}