package com.microsv.task_service.service;

import com.microsv.task_service.dto.request.SubscriptionRequest;
import com.microsv.task_service.entity.PushSubscription;

public interface NotificationService {
    void checkDeadlinesAndSendNotifications();
    void sendNotification(PushSubscription subscription, String payload);
    void subscribe(SubscriptionRequest request, Long userId);
    void unsubscribe( Long userId);
}
