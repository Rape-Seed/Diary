package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.InfoResponseDto;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.notification.dto.NotificationPagingDto;
import org.springframework.data.domain.Pageable;

public interface MemberService {


    InfoResponseDto getMyInfo(Member member);

    InfoResponseDto updateMyInfo(Member member, MyInfoRequestDto dto);

    String getMemberCode(Member member);

    InfoResponseDto getMemberInfo(Member member, Long member_id);

    void withdrawMembership(Member member);

    NotificationPagingDto notificationList(Member member, Pageable pageable);
}
