package com.example.diary.global.auth.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.advice.exception.MemberNotFoundException;
import com.example.diary.global.auth.entity.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);
        if (member == null) {
            throw new MemberNotFoundException("[ERROR] 가입되지 않은 맴버입니다.");
        }
        return new CustomUserDetails(member);
    }
}
