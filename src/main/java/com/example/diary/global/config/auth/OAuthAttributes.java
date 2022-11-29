package com.example.diary.global.config.auth;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.member.entity.PlatForm;
import com.example.diary.domain.member.entity.Role;
import java.time.LocalDate;
import java.util.Map;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String name;
    private String email;

    public static OAuthAttributes of(String registrationId, String userNameAttributeName,
                                     Map<String, Object> attributes) {
        return ofGoogle(userNameAttributeName, attributes);
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name(String.valueOf(attributes.get("name")))
                .email(String.valueOf(attributes.get("email")))
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .build();
    }

    public Member toEntity() {
        return Member.builder()
                .name(name)
                .email(email)
                .code(makeCode())
                .birthday(LocalDate.now())
                .role(Role.GUEST)
                .platForm(PlatForm.GOOGLE)
                .build();
    }

    private String makeCode() {
        StringBuilder code = new StringBuilder();
        for (String codeStep : UUID.randomUUID().toString().split("-")) {
            code.append(codeStep, 0, 2);
        }
        return String.valueOf(code);
    }

}
