package com.microsv.task_service.service;

import com.microsv.task_service.dto.request.TaskCreationRequest;
import com.microsv.task_service.dto.request.TaskUpdateRequest;
import com.microsv.task_service.dto.response.*;
import com.microsv.task_service.entity.Task;
import com.microsv.task_service.enumeration.TaskStatus;

import java.util.List;

public interface TaskService {
    TaskResponse createTask(TaskCreationRequest request, Long userId);
    TaskResponse getTask(Long taskId, Long userId);
    List<TaskResponse> getAllTasksByUser(Long userId);
    List<TaskResponse> getTasksByStatus(Long userId, TaskStatus status);
    void deleteTask(Long taskId, Long userId);
    TaskResponse updateTask(Long taskId, TaskUpdateRequest request, Long userId);
    TaskStatisticResponse getTaskStatistics(Long userId);
    TaskResponse updateTaskStatus(Long taskId, TaskStatus status, Long userId);
    List<TaskResponse> getUpcomingTasks(Long userId, Integer hours);
    List<TaskResponse> getAllTaskToday(Long userId);
    List<TaskResponse> getOverdueTaskToday(Long userId);
    List<TaskResponse> getCompetedTaskToday(Long userId);
    Double getCompletionRateThisWeek(Long userId);
    Double getFreeHoursThisWeek(Long userId);
    StatusTaskWeekResponse getWeeklyTaskRates(Long userId);
    List<DailyTaskCountResponse> getWeeklyTaskDistribution(Long userId);
    List<TaskTimelineResponse> getTaskCreationTimeline(Long userId);
    Long countActiveUsersThisWeek();
    Long countTasksCreatedThisWeek();
    List<DailyCompletedTasksResponse> getCompletedTasksByDayThisWeek();
    List<TaskPriorityCountResponse> countTasksByPriority();
    List<Task> findTaskByTitle(String title);
}