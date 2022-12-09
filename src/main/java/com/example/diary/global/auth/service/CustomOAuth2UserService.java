package com.example.diary.global.auth.service;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import com.example.diary.domain.member.repository.MemberRepository;
import com.example.diary.global.auth.entity.CustomUserDetails;
import com.example.diary.global.auth.info.OAuth2UserInfo;
import com.example.diary.global.auth.info.OAuth2UserInfoFactory;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;


    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        try {
            return this.process(userRequest, oAuth2User);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InternalAuthenticationServiceException(e.getMessage(), e.getCause());
        }
    }

    private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
        PlatformType platformType = PlatformType.find(
                userRequest.getClientRegistration().getRegistrationId().toLowerCase());

        OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(platformType, oAuth2User.getAttributes());
        Member member = memberRepository.findByEmail(userInfo.getEmail())
                .map(entity -> entity.update(userInfo))
                .orElse(createMember(userInfo, platformType));

        log.info(member.getEmail() + "(님) 입니다.");
        memberRepository.save(member);

        return new CustomUserDetails(member, oAuth2User.getAttributes());
    }

    private Member createMember(OAuth2UserInfo userInfo, PlatformType platformType) {
        //TODO code값 랜덤으로 넣는 로직 추가
        return Member.builder()
                .name(userInfo.getName())
                .email(userInfo.getEmail())
                .birthday(LocalDate.parse(userInfo.getBirthday(), DateTimeFormatter.ISO_DATE))
                .platform(platformType)
                .role(Role.MEMBER)
                .build();
    }
}
