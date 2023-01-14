package com.example.diary.domain.notification.controller;

import com.example.diary.domain.member.entity.CurrentMember;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.notification.dto.NotificationPagingDto;
import com.example.diary.domain.notification.service.NotificationService;
import com.example.diary.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/v1/members/notifications")
    public ResponseDto<NotificationPagingDto> notificationList(@CurrentMember Member member,
                                                               Pageable pageable) {
        return new ResponseDto<>(notificationService.notificationList(member, pageable), HttpStatus.OK);
    }
}
