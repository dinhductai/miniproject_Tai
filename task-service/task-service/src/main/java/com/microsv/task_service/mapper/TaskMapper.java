package com.microsv.task_service.mapper;

import com.microsv.task_service.dto.request.TaskCreationRequest;
import com.microsv.task_service.dto.response.TaskResponse;
import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.PriorityLevel;
import com.microsv.task_service.enumeration.TaskStatus;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {
    public Task taskCreationRequestToTask(TaskCreationRequest request, Long userId){
        return Task.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .deadline(request.getDeadline())
                .priority(request.getPriority() != null ? request.getPriority() : PriorityLevel.MEDIUM)
                .status(TaskStatus.TODO)
                .userId(userId)
                .build();
    }

    public TaskResponse toTaskResponse(Task task) {
        return TaskResponse.builder()
                .taskId(task.getTaskId())
                .title(task.getTitle())
                .description(task.getDescription())
                .deadline(task.getDeadline())
                .status(task.getStatus())
                .priority(task.getPriority())
                .createdAt(task.getCreatedAt())
                .completedAt(task.getCompletedAt())
                .userId(task.getUserId())
                .build();
    }
}
