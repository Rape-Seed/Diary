package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.dto.MyInfoResponseDto;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public MyInfoResponseDto getMyInfo(Member member) {
        return new MyInfoResponseDto(member);
    }

    @Override
    public MyInfoResponseDto updateMyInfo(Member member, MyInfoRequestDto dto) {
        return new MyInfoResponseDto(memberRepository.save(member.update(dto)));
    }
}
