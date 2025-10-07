package com.microsv.task_service.dto.response;

import com.microsv.task_service.enumeration.PriorityLevel;
import com.microsv.task_service.enumeration.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TaskResponse {
    private Long taskId;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private TaskStatus status;
    private PriorityLevel priority;
    private LocalDateTime createdAt;
    private Long userId;
}