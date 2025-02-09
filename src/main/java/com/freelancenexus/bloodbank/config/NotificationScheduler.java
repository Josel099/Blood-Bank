package com.freelancenexus.bloodbank.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.freelancenexus.bloodbank.service.NotificationService;

@Component
public class NotificationScheduler {

    private final NotificationService notificationService;


    /**
     * @param notificationService
     */
    public NotificationScheduler(NotificationService notificationService) {
        super();
        this.notificationService = notificationService;
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void scheduleNotificationCheck() {
        notificationService.checkAndAddNotifications();
    }

}
