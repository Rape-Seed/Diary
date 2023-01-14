package com.example.diary.domain.notification.entity;

public enum NotificationType {
    RELATION_APPLY("new relation"),
    RELATION_ACCEPT("accept relation"),
    TEAM_APPLY("new team"),
    ;

    private final String notificationText;

    NotificationType(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationText() {
        return notificationText;
    }
}
