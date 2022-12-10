package com.example.diary.global.auth.entity;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatformType;
import com.example.diary.domain.member.entity.Role;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

public class CustomUserDetails implements UserDetails, OAuth2User {
    private final String email;
    private final String code;
    private final PlatformType platformType;
    private final Role role;
    private final Collection<GrantedAuthority> authorities;
    private Map<String, Object> attributes;

    public CustomUserDetails(Member member) {
        this.email = member.getEmail();
        this.code = member.getCode();
        this.platformType = member.getPlatform();
        this.role = member.getRole();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.MEMBER.name()));
    }

    public CustomUserDetails(Member member, Map<String, Object> attributes) {
        this.email = member.getEmail();
        this.code = member.getCode();
        this.platformType = member.getPlatform();
        this.role = member.getRole();
        this.authorities = Collections.singletonList(new SimpleGrantedAuthority(Role.MEMBER.name()));
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getName() {
        return email;
    }

    @Override
    public String getPassword() {
        return this.code;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
