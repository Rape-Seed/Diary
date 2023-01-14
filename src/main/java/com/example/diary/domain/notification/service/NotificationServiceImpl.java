package com.example.diary.domain.notification.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.notification.dto.NotificationDto;
import com.example.diary.domain.notification.dto.NotificationPagingDto;
import com.example.diary.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    @Override
    public NotificationPagingDto notificationList(Member member, Pageable pageable) {
        return new NotificationPagingDto(
                notificationRepository.findAllByMember(member, pageable).map(NotificationDto::new));
    }
}
