package com.microsv.task_service.service.impl;

import com.microsv.task_service.entity.PushSubscription;
import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.TaskStatus;
import com.microsv.task_service.repository.PushSubscriptionRepository;
import com.microsv.task_service.repository.TaskRepository;
import com.microsv.task_service.service.NotificationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    TaskRepository taskRepository;
    PushSubscriptionRepository subscriptionRepository;


    @Override
    @Scheduled(fixedRate = 900000) // 900000ms = 15 phút
    public void checkDeadlinesAndSendNotifications() {
        System.out.println("Scanning for upcoming deadlines...");
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inOneHour = now.plusHours(1);

        // Tìm các task sắp đến hạn trong vòng 1 giờ tới
        List<Task> upcomingTasks = taskRepository.findAllByDeadlineBetweenAndStatus(now, inOneHour, TaskStatus.TODO);

        for (Task task : upcomingTasks) {
            // Lấy thông tin subscription của user
            List<PushSubscription> subscriptions = subscriptionRepository.findAllByUserId(task.getUserId());

            // Logic gửi push notification đến các subscription này
            // ...
            System.out.println("Sending notification for task: " + task.getTitle());
        }
    }
}
