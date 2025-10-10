package com.microsv.task_service.mapper;

import com.microsv.task_service.dto.request.SubscriptionRequest;
import com.microsv.task_service.entity.PushSubscription;
import nl.martijndwars.webpush.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public PushSubscription toPushSubscription(SubscriptionRequest request,Long userId) {
        return PushSubscription.builder()
                .userId(userId)
                .endpoint(request.getEndpoint())
                .p256dh(request.getP256dh())
                .auth(request.getAuth())
                .build();
    }

}
