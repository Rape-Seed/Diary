package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.InfoResponseDto;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.entity.Member;

public interface MemberService {


    InfoResponseDto getMyInfo(Member member);

    InfoResponseDto updateMyInfo(Member member, MyInfoRequestDto dto);

    String getMemberCode(Member member);

    InfoResponseDto getMemberInfo(Member member, Long member_id);

    void withdrawMembership(Member member);
}
