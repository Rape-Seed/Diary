package com.example.diary.domain.notification.dto;

import com.example.diary.domain.member.entity.Member;
import com.example.diary.domain.notification.entity.Notification;
import com.example.diary.domain.notification.entity.NotificationArgs;
import com.example.diary.domain.notification.entity.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {

    private Long id;
    private String title;
    private String message;
    private String link;
    private boolean checked;
    private NotificationArgs args;
    private Member member;
    private NotificationType notificationType;

    public NotificationDto(Notification notification) {
        this.id = notification.getId();
        this.title = notification.getTitle();
        this.message = notification.getMessage();
        this.link = notification.getLink();
        this.checked = notification.isChecked();
        this.args = notification.getArgs();
        this.member = notification.getMember();
        this.notificationType = notification.getNotificationType();
    }
}
