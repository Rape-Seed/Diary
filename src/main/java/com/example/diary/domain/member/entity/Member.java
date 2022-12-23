package com.example.diary.domain.member.entity;

import com.example.diary.domain.diary.entity.Diary;
import com.example.diary.domain.member.dto.MyInfoRequestDto;
import com.example.diary.domain.relation.entity.Relation;
import com.example.diary.domain.team.entity.TeamMember;
import com.example.diary.global.auth.info.OAuth2UserInfo;
import com.example.diary.global.common.BaseEntity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Lob
    private String profileImage;

    private String email;

    @Column(unique = true)
    private String code;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    private PlatformType platform;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<Diary> diaries = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Relation> relations = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<TeamMember> teamMembers = new ArrayList<>();

    public Member update(OAuth2UserInfo userInfo) {
        if (userInfo.getName() != null && !this.name.equals(userInfo.getName())) {
            this.name = userInfo.getName();
        }
        LocalDate birthday = LocalDate.parse(userInfo.getBirthday(), DateTimeFormatter.ISO_DATE);
        if (userInfo.getBirthday() != null && !this.birthday.equals(birthday)) {
            this.birthday = birthday;
        }
        if (userInfo.getEmail() != null && !this.email.equals(userInfo.getEmail())) {
            this.email = email;
        }

        return this;
    }

    public Member update(MyInfoRequestDto dto) {
        if (!this.name.equals(dto.getName())) {
            this.name = dto.getName();
        }
        LocalDate birthday = LocalDate.parse(dto.getBirthday());
        if (!this.birthday.isEqual(birthday)) {
            this.birthday = birthday;
        }
        if (!this.profileImage.equals(dto.getProfileImage())) {
            this.profileImage = dto.getProfileImage();
        }

        return this;
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", email='" + email + '\'' +
                ", code='" + code + '\'' +
                ", birthday=" + birthday +
                ", platform=" + platform +
                ", role=" + role +
                '}';
    }
}
