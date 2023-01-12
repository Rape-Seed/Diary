package com.example.diary.domain.notification.entity;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.global.common.BaseEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Notification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notification_id")
    private Long id;
    private String title;
    private String message;
    private String link;
    private boolean checked;
    @Type(type = "json")
    @Column(columnDefinition = "json")
    private NotificationArgs args;
    @ManyToOne
    private Member member;
    @Enumerated(EnumType.STRING)
    private NotificationType notificationType;

    public Notification(String title, String message, String link, boolean checked,
                        NotificationArgs args, Member member, NotificationType notificationType) {
        this.title = title;
        this.message = message;
        this.link = link;
        this.checked = checked;
        this.args = args;
        this.member = member;
        this.notificationType = notificationType;
    }
}
