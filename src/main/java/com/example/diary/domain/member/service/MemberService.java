package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.dto.MyInfoResponseDto;
import com.example.diary.domain.member.entity.Member;

public interface MemberService {


    MyInfoResponseDto getMyInfo(Member member);

    MyInfoResponseDto updateMyInfo(Member member, MyInfoRequestDto dto);

    String getMemberCode(Member member);
}
