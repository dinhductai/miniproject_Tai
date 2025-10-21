package com.microsv.task_service.dto.request;

import com.microsv.task_service.enumeration.PriorityLevel;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class TaskCreationRequest {
//    @NotBlank(message = "Title is required")
    private String title;
    private String description;
//    @Future(message = "Deadline must be in the future")

    private OffsetDateTime startTime;
//    @NotBlank(message = "Deadline is required")
    private OffsetDateTime deadline;
//    @NotBlank(message = "Priority is required")
    private PriorityLevel priority;
}