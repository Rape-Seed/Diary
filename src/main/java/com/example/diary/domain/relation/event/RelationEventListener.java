package com.example.diary.domain.relation.event;

import com.example.diary.domain.notification.entity.Notification;
import com.example.diary.domain.notification.entity.NotificationArgs;
import com.example.diary.domain.notification.entity.NotificationType;
import com.example.diary.domain.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Async
@Component
@Transactional
@RequiredArgsConstructor
public class RelationEventListener {

    private final NotificationRepository notificationRepository;

    @EventListener
    public void handleRelationCreateEvent(RelationCreatedEvent relationCreatedEvent) {
        Notification notificationByFriend = new Notification(
                "친구 신청",
                relationCreatedEvent.getMember().getName() + " 님이 " + relationCreatedEvent.getFriend().getName()
                        + "님과 친구가 되길 희망해요",
                "",
                false,
                new NotificationArgs(relationCreatedEvent.getMember().getId(),
                        relationCreatedEvent.getFriend().getId()),
                relationCreatedEvent.getFriend(),
                NotificationType.RELATION_APPLY
        );
        notificationRepository.save(notificationByFriend);
    }
}


