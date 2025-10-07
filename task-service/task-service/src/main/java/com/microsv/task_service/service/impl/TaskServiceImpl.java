package com.microsv.task_service.service.impl;

import com.microsv.task_service.dto.request.TaskCreationRequest;
import com.microsv.task_service.dto.response.TaskResponse;
import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.PriorityLevel;
import com.microsv.task_service.enumeration.TaskStatus;
import com.microsv.task_service.repository.TaskRepository;
import com.microsv.task_service.service.NotificationService;
import com.microsv.task_service.service.TaskService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    TaskRepository taskRepository;
    NotificationService notificationService; // Sẽ thêm sau

    @Override
    public TaskResponse createTask(TaskCreationRequest request, Long userId) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setDeadline(request.getDeadline());
        task.setPriority(request.getPriority() != null ? request.getPriority() : PriorityLevel.MEDIUM);
        task.setStatus(TaskStatus.TODO);
        task.setUserId(userId);

        Task savedTask = taskRepository.save(task);

        // Gửi thông báo khi tạo task mới (sẽ làm ở bước sau)
        // notificationService.sendNotificationForNewTask(savedTask);

        return toTaskResponse(savedTask);
    }

    @Override
    public TaskResponse getTask(Long taskId, Long userId) {
        return null;
    }

    @Override
    public List<TaskResponse> getAllTasksByUser(Long userId) {
        return List.of();
    }

    @Override
    public TaskResponse updateTask(Long taskId, TaskCreationRequest request, Long userId) {
        return null;
    }

    @Override
    public void deleteTask(Long taskId, Long userId) {

    }

    // ... triển khai các phương thức còn lại (get, update, delete)

    private TaskResponse toTaskResponse(Task task) {
        // Logic chuyển đổi từ Task Entity sang TaskResponse DTO
        // ...
        return null;
    }
}
