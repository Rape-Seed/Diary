package com.example.diary.domain.member.service;

import com.example.diary.domain.member.dto.InfoResponseDto;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.relation.repository.CustomRelationRepository;
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

    private final CustomRelationRepository customRelationRepository;

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
        Member friend = memberRepository.findById(member_id).orElseThrow(MemberNotFoundException::new);
        Relation relation = customRelationRepository.findRelationByDoubleId(member.getId(), member_id);
        if (!relation.getFriend().equals(friend)) {
            throw new FriendNotAuthorizedException();
        }

        return new InfoResponseDto(friend);
    }

    @Override
    @Transactional
    public void withdrawMembership(Member member) {
        memberRepository.delete(member);
    }
}
