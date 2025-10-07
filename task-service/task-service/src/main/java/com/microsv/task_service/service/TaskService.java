package com.microsv.task_service.service;

import com.microsv.task_service.dto.request.TaskCreationRequest;
import com.microsv.task_service.dto.response.TaskResponse;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskCreationRequest request, Long userId);
    TaskResponse getTask(Long taskId, Long userId);
    List<TaskResponse> getAllTasksByUser(Long userId);
    TaskResponse updateTask(Long taskId, TaskCreationRequest request, Long userId);
    void deleteTask(Long taskId, Long userId);
}