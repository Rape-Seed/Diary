package com.example.diary.domain.notification.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.diary.WithMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.notification.dto.NotificationPagingDto;
import com.example.diary.domain.notification.entity.Notification;
import com.example.diary.domain.notification.entity.NotificationArgs;
import com.example.diary.domain.notification.entity.NotificationType;
import com.example.diary.domain.notification.repository.NotificationRepository;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.entity.RelationType;
import com.example.diary.domain.relation.repository.RelationRepository;
import com.example.diary.global.utils.RandomUtils;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class NotificationServiceImplTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    RelationRepository relationRepository;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    NotificationService notificationService;

    private Member savedMember1;
    private Member savedMember2;

    @BeforeEach
    public void before() {
        Member member1 = makeMember("홍길동", "gil@gmail.com", "1q2w3e4r", "2000-01-01");
        Member member2 = makeMember("이길자", "gi2@gmail.com", "1q2w3e4r2", "2000-01-01");
        Relation relation = new Relation(member1, member2, RelationType.ACCEPT);
        savedMember1 = memberRepository.save(member1);
        savedMember2 = memberRepository.save(member2);
        relationRepository.save(relation);
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
    @WithMember("swchoi1997@naver.com")
    void notificationTest() {
        Member member = memberRepository.findByEmail("swchoi1997@naver.com");
//        Member friend = memberRepository.findByEmail("gil@gmail.com");
        Member friend = memberRepository.findById(savedMember1.getId()).orElseThrow();

        makeNotification(member, friend);
        PageRequest pageRequest = PageRequest.of(0, 20);
        NotificationPagingDto notificationPagingDto = notificationService.notificationList(friend, pageRequest);

        assertThat(notificationPagingDto.getNotificationResponse().size()).isEqualTo(1);

    }

    private void makeNotification(Member member, Member friend) {
        Notification notification = new Notification(
                "친구 신청",
                member.getName() + " 님이 " + friend.getName() + "님과 친구가 되길 희망해요",
                "",
                false,
                new NotificationArgs(member.getId(), friend.getId()),
                friend,
                NotificationType.RELATION_APPLY
        );

        notificationRepository.save(notification);
    }

}