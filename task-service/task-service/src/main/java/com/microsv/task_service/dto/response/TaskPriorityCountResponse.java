package com.microsv.task_service.dto.response;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskPriorityCountResponse {
    private String priorityLevel;
    private Long taskCount;
}