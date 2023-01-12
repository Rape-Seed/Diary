package com.example.diary.domain.notification.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationArgs {

    private Long memberId;
    private Long friendId;
}
