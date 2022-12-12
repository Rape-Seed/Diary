package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.InfoResponseDto;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.advice.exception.FriendNotAuthorizedException;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public InfoResponseDto getMyInfo(Member member) {
        return new InfoResponseDto(member);
    }

    @Override
    @Transactional
    public InfoResponseDto updateMyInfo(Member member, MyInfoRequestDto dto) {
        return new InfoResponseDto(memberRepository.save(member.update(dto)));
    }

    @Override
    public String getMemberCode(Member member) {
        return member.getCode();
    }

    @Override
    public InfoResponseDto getMemberInfo(Member member, Long member_id) {
        Member getMember = memberRepository.findById(member_id).orElseThrow(MemberNotFoundException::new);
        if (!member.getRelations().contains(getMember)) {
            throw new FriendNotAuthorizedException();
        }

        return new InfoResponseDto(getMember);
    }
}
