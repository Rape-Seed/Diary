package com.example.diary.domain.notification.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.notification.dto.NotificationPagingDto;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationPagingDto notificationList(Member member, Pageable pageable);
}
