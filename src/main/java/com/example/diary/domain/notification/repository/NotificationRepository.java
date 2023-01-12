package com.example.diary.domain.notification.repository;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findAllByMember(Member member, Pageable pageable);

    Long countByMemberAndChecked(Member member, boolean checked);
}
