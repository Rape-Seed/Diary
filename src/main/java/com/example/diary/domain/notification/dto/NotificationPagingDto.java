package com.example.diary.domain.notification.dto;

import com.example.diary.domain.notification.entity.NotificationArgs;
import com.example.diary.domain.notification.entity.NotificationType;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

@Getter
@Setter
public class NotificationPagingDto {

    private int totalPageCount;
    private int currentPageCount;
    private long totalElementCount;
    private int currentPageElementCount;
    private List<NotificationResponse> notificationResponse = new ArrayList<>();

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    static class NotificationResponse {
        private Long id;
        private String title;
        private String message;
        private String link;
        private boolean checked;
        private NotificationArgs args;
        private NotificationType notificationType;

        public NotificationResponse(NotificationDto notificationDto) {
            this.id = notificationDto.getId();
            this.title = notificationDto.getTitle();
            this.message = notificationDto.getMessage();
            this.link = notificationDto.getLink();
            this.checked = notificationDto.isChecked();
            this.args = notificationDto.getArgs();
            this.notificationType = notificationDto.getNotificationType();
        }
    }

    public NotificationPagingDto(Page<NotificationDto> notificationResult) {
        this.totalPageCount = notificationResult.getTotalPages();
        this.currentPageCount = notificationResult.getNumber();
        this.totalElementCount = notificationResult.getTotalElements();
        this.currentPageElementCount = notificationResult.getNumberOfElements();
        this.notificationResponse = notificationResult.map(NotificationResponse::new).getContent();
    }

    public List<NotificationResponse> fromNotificationDto(List<NotificationDto> dtos) {
        List<NotificationResponse> response = new ArrayList<>();
        for (NotificationDto dto : dtos) {
            response.add(new NotificationResponse(dto));
        }
        return response;
    }
}
